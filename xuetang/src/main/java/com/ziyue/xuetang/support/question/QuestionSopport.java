package com.ziyue.xuetang.support.question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageInfo;
import com.ziyue.xuetang.constant.Config;
import com.ziyue.xuetang.constant.RspCode;
import com.ziyue.xuetang.exception.ValidateException;
import com.ziyue.xuetang.model.question.QuestionInfo;
import com.ziyue.xuetang.model.question.QuestionReports;
import com.ziyue.xuetang.service.question.QuestionInfoService;
import com.ziyue.xuetang.service.user.UserService;
import com.ziyue.xuetang.utils.FileUtil;
import com.ziyue.xuetang.utils.JacksonUtil;
import com.ziyue.xuetang.utils.StringUtil;
import com.ziyue.xuetang.utils.TimeUtil;

/**
 * @描述： 问题支撑
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年12月17日
 * @version v1.0.
 * 
 */

@Component
public class QuestionSopport {
	
	@Resource
	private QuestionInfoService questionService;
	
	@Resource
	private UserService userService;

	private static Logger logger = Logger.getLogger(QuestionSopport.class);
	
	public Map<String, Object> checkParams(Map<String, Object> paramMap) throws ValidateException{
		
		String queryParams = (String) paramMap.get("queryParams");
		int questionId = Integer.valueOf(String.valueOf(paramMap.get("questionId")));
		QuestionInfo info = questionService.selectByPrimaryKey(questionId);
		
		if(queryParams.equals(Config.ONE)){
			if(info == null){
				throw new ValidateException(RspCode.CODE_PARAM, "questionId = " + questionId + "不存在");
			}
			List<Integer> userIds = new ArrayList<>();
			userIds.add(info.getUserId());
			if(!StringUtils.isEmpty(info.getAdoptUserId())){
				userIds.add(info.getAdoptUserId());
			}
			paramMap.put("questioner", userIds);
		}
		if(queryParams.equals(Config.TWO)){
			if(!StringUtils.isEmpty(paramMap.get("nickName"))){
				paramMap.put("userName", paramMap.get("nickName"));
			}
		}
		return paramMap;
	}
	
	
	public List<Map<String, Object>> getAnswerList(PageInfo<Map<String,Object>> pageInfo){
		List<Map<String, Object>> answerList = new ArrayList<>();
		//从数据库获取该问题的一级回复
		List<Map<String, Object>> answeListlevelOne = pageInfo.getList();
		for (Map<String, Object> map : answeListlevelOne) {
			//构建返回一级回复的json
			Map<String, Object> levelOneMap = new HashMap<>();
			levelOneMap.put("answerId", map.get("id"));
			levelOneMap.put("answer", map.get("nike_name"));
			levelOneMap.put("answerLogo", map.get("head_url"));
			levelOneMap.put("userId", map.get("user_id"));
			levelOneMap.put("answerTime", TimeUtil.getTimeByMillis(map.get("create_time")));
			levelOneMap.put("picList", getAnswerPicList(StringUtil.getIntValue(map, "id")));
			levelOneMap.put("content", map.get("content"));
			levelOneMap.put("markdownCont", map.get("markdown_cont"));
			levelOneMap.put("children", getAnswerLevelTwoList(
							Integer.valueOf(String.valueOf(map.get("question_id"))), 
							Integer.valueOf(String.valueOf(map.get("id")))));
			answerList.add(levelOneMap);
		}
		
		return answerList;
	}
	
	public List<String> getAnswerPicList(int answerId){
		List<String> list = new  ArrayList<>();
		try {
			List<Map<String, Object>> listPic = questionService.getQuestionAnswerPicList(answerId);
			for (Map<String, Object> map : listPic) {
				list.add(StringUtil.getStringValue(map, "pic_url"));
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return list;
	}
	
	public List<Map<String, Object>> getAnswerLevelTwoList(int questionId, int answerLevelOneId){
		List<Map<String, Object>> answerList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("questionId", questionId);
		map.put("fid", answerLevelOneId);
		List<Map<String, Object>> answerLevelTwoList = questionService.querQuestionAnswerLevelTwo(map);
		for (Map<String, Object> answer : answerLevelTwoList) {
			Map<String, Object> answerMap = new HashMap<>();
			answerMap.put("answerId", answer.get("id"));
			answerMap.put("answer", answer.get("nike_name"));
			answerMap.put("answerUserId", answer.get("user_id"));
			answerMap.put("answerLogo", answer.get("head_url"));
			answerMap.put("toAnswer", answer.get("toAnswer"));
			answerMap.put("toAnswerUserId", answer.get("to_user_id"));
			answerMap.put("content", answer.get("content"));
			answerMap.put("markdownCont", answer.get("markdown_cont"));
			answerMap.put("questionId", answer.get("question_id"));
			answerMap.put("answerTime", TimeUtil.getTimeByMillis(answer.get("create_time")));
			
			answerList.add(answerMap);
		}
		return answerList;
	}
	
	
	/**
	 * 采纳
	 */
	public void adpot(Map<String, Object> map) throws ValidateException{
		int questionId = StringUtil.getIntValue(map, "questionId");
		int answerId = StringUtil.getIntValue(map, "answerId");
		int answerUserId = StringUtil.getIntValue(map, "answerUserId");
		int userId = StringUtil.getIntValue(map, "userId");
		
		if(answerUserId == userId){
			throw new ValidateException(RspCode.QUESTION_ADOP_MYSELF, "不能采纳自己的回答");
		}
		
		QuestionInfo question = new QuestionInfo();
		question.setId(questionId);
		question.setAdoptAnswerId(answerId);
		question.setAdoptUserId(answerUserId);
//		question.setFristAnswerTime(TimeUtil.currentTimeMillisStr());
		
		questionService.updateByPrimaryKeySelective(question);
		
	}
	
	/**
	 * 取消采纳
	 * @param map
	 */
	public void cancelAdopt(Map<String, Object> map) throws ValidateException {
		int questionId = StringUtil.getIntValue(map, "questionId");
		
		questionService.cancleAdopt(questionId);
	}
	
	
	/**
	 * 举报
	 * @param map
	 * @throws ValidateException
	 */
	public void reports(Map<String, Object> map) throws ValidateException {
		int questionId = StringUtil.getIntValue(map, "questionId");
		String flag = StringUtil.getStringValue(map, "flag");
		int type = StringUtil.getIntValue(map, "type");
		int userId = StringUtil.getIntValue(map, "userId");
		
		if(!checkType(type)){
			throw new ValidateException(RspCode.CODE_PARAM, "type参数必须在1 - 5");
		}
		
		QuestionReports report = new QuestionReports();
		report.setQuestionId(questionId);
		report.setUserId(userId);
		report.setReportType(String.valueOf(flag));
		report.setType(String.valueOf(type));
		
		//举报问题
		if(flag.equals(Config.ONE)){
			report.setReportType(Config.ONE);
		}
		
		//举报回复
		if(flag.equals(Config.ZERO)){
			if(StringUtils.isEmpty(map.get("answerId"))){
				throw new ValidateException(RspCode.CODE_PARAM, "answerId 不能为空");
			}
			int answerId = StringUtil.getIntValue(map, "answerId");
			report.setAnswerId(answerId);
			report.setReportType(Config.ZERO);
		}
		
		questionService.reports(report);
		
	}
	
	public boolean checkType(int type){
		if(type != 1 && type != 2 && type != 3 && type != 4 && type != 5 ){
			logger.info("举报问题：检查type在模板中未找到" + type);
			return false;
		}
		return true;
	}
	
	/**
	 * 创建问题，复制图片，并插入前三张到数据库
	 * @param pictList
	 * @param questionId
	 * @param userId
	 */
	public void insertQuestionPic(List<String> pictList, int questionId, int userId){
		int i = 0;
		for (String tempUrl : pictList) {
			String url = null;
			try {
				url = FileUtil.copyFile(Config.FILE_PATH_QUESTION, userId+"", tempUrl);
			} catch (Exception e) {
				logger.error("创建问题，拷贝图片出错", e);
			}
			try {
				if(i < 3 && !StringUtils.isEmpty(url)){
					Map<String, Object> picMap = new HashMap<String, Object>();
					picMap.put("questionId", questionId);
					picMap.put("picUrl", url);
					questionService.insertQuestionPic(picMap);
					i++;
				}
				
			} catch (Exception e) {
				logger.error("创建问题，插入图片出错", e);
			}
		}
	}
	
	/**
	 * 评论回复的时候，插入图片
	 * @param pictList
	 * @param questionId
	 * @param userId
	 */
	public void insertQuestionAnswerPic(List<String> pictList, int anserId, int userId){
		int i = 0;
		for (String tempUrl : pictList) {
			String url = null;
			try {
				url = FileUtil.copyFile(Config.FILE_PATH_QUESTION, userId+"", tempUrl);
			} catch (Exception e) {
				logger.error("回复问题，拷贝图片出错", e);
			}
			try {
				if(i < 3 && !StringUtils.isEmpty(url)){
					Map<String, Object> picMap = new HashMap<String, Object>();
					picMap.put("answerId", anserId);
					picMap.put("picUrl", url);
					questionService.insertQuestionAnswerPic(picMap);
					i++;
				}
				
			} catch (Exception e) {
				logger.error("回复问题，插入图片出错", e);
			}
		}
	}
		
}
