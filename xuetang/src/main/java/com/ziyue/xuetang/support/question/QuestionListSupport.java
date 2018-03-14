package com.ziyue.xuetang.support.question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageInfo;
import com.ziyue.xuetang.constant.Config;
import com.ziyue.xuetang.exception.ValidateException;
import com.ziyue.xuetang.model.question.QuestionInfo;
import com.ziyue.xuetang.model.question.QuestionShare;
import com.ziyue.xuetang.model.question.QuestionTags;
import com.ziyue.xuetang.model.user.UserAccounts;
import com.ziyue.xuetang.model.user.UserCollectionsQuestion;
import com.ziyue.xuetang.service.question.QuestionInfoService;
import com.ziyue.xuetang.service.user.UserService;
import com.ziyue.xuetang.utils.StringUtil;
import com.ziyue.xuetang.utils.TimeUtil;

/**
 * @描述：用于查询问题列表
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2018年1月18日
 * @version v1.0.
 * 
 */
@Component
public class QuestionListSupport {

	private static Logger logger = Logger.getLogger(QuestionListSupport.class);
	
	//用于标记当前用户id
	private int currentUserId = 0;
	//用于标记当前执行的类型，如首页列表、我的提问列表、我参与的列表等
	private String currentType = "0";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QuestionInfoService questionService;
	
	/**
	 * 用于获取关于问题的列表</br>
	 * <ul>
	 * 	<li>首页问题列表</li>
	 * 	<li>我的收藏 - 问题列表</li>
	 * 	<li>参与回答 - 问题列表</li>
	 * 	<li>我的提问 - 问题列表</li>
	 * 	<li>举报问题 - 问题列表</li>
	 * </ul>
	 * @param type 需要执行的类型
	 * @param question	满足需求的问题集合
	 * @param bean		用于封装参数
	 * @return
	 */
	public List<Map<String, Object>> getQuestionList(String type, PageInfo<QuestionInfo> question, QuestionListSupportBean bean){
		List<Map<String, Object>> list = new ArrayList<>();
		
		/**
		 * 1、 条件获取问题的数据
		 * 2、 根据获取的所有的问题，分别查找问题的基本信息
		 * 3、 根据获取的所有的问题，分别查找用户的基本信息
		 */
		try {
			if(StringUtil.isEmpty(type)){
				type = Config.ZERO;
			}
			this.currentType = type;
			this.currentUserId = bean.getCurrentUserId();
			list = getIndexList(question);
		} catch (Exception e) {
			logger.error("", e);
		}
		
		
		return list;
	}
	
	/**
	 * 获取首页的问题列表
	 * @param paramMap
	 * @return
	 * @throws ValidateException
	 */
	public List<Map<String, Object>> getIndexList(PageInfo<QuestionInfo> question) throws ValidateException{
		List<Map<String, Object>> list = new ArrayList<>();
		if(question.getTotal() > 0){
			List<QuestionInfo> questionList = question.getList();
			for (QuestionInfo questionInfo : questionList) {
				Map<String, Object> map = bulidMap();
				Map<String, Object> questionMap = bulidMap();
				try {
					questionMap = getQuestion(questionInfo);
				} catch (Exception e) {
					logger.error("问题转json出错", e);
				}
				
				map.put("questionInfo", questionMap);
				map.put("userInfo", getUserInfo(currentUserId, questionInfo.getUserId()));
				
				//举报类
				//如果是举报则封装举报的信息
				if(this.currentType.equals(Config.QUESTION_REPORT_LIST)){
					
				}
				list.add(map);
			}
		}
		return list;
	}
	
	/**
	 * 获取首页列表中Question对象
	 * @param questionInfo
	 * @return
	 */
	public Map<String, Object> getQuestion(QuestionInfo questionInfo){
		Map<String, Object> map = bulidMap();
		map.put("id", questionInfo.getId());
		map.put("title", questionInfo.getTitle());
		map.put("content", questionInfo.getContent());
		map.put("markdownCont", questionInfo.getMarkdownCont());
		map.put("anserCount", questionInfo.getAnswerCount());
		map.put("rewardCount", questionInfo.getRewardCount());
		map.put("flag", questionInfo.getIsDelete());
		map.put("createTime", TimeUtil.getTimeByMillis(Long.valueOf(questionInfo.getCreateTime() + "000")));
		
		//是否收藏
		map.put("isCollection", isCollection(questionInfo.getId(), currentUserId));
		//图片列表
		map.put("picList", getPicList(questionInfo.getId()));
		//标签列表
		map.put("tagsList", getTagsList(questionInfo.getId()));
		//分享列表
		map.put("shareList", getShareList(questionInfo.getId()));
		return map;
	}
	
	/**
	 * 判断用户是否收藏此问题
	 * @param questionId
	 * @param userId
	 * @return 返回0未搜藏，返回1搜藏
	 */
	public int isCollection(int questionId, int userId){
		UserCollectionsQuestion queryUCQ = new UserCollectionsQuestion();
		queryUCQ.setQuestionId(questionId);
		queryUCQ.setUserId(userId);
		UserCollectionsQuestion ucq = null;
		try {
			ucq = userService.getCollectionQuestion(queryUCQ);
		} catch (Exception e) {
			logger.info("查询用户收藏问题：questionId = " + questionId + "; userId = " + userId);
			logger.error("", e);
		}
		if(ucq != null){
			return 1;
		}
		return 0;
	}
	
	/**
	 * 获取图片列表
	 * @param questionId
	 * @return
	 */
	public List<String> getPicList(int questionId){
		List<String> list = new ArrayList<>();
		try {
			List<Map<String, Object>> listPic = questionService.getQuestionPicList(questionId);
			for (Map<String, Object> map : listPic) {
				list.add(StringUtil.getStringValue(map, "pic_url"));
			}
		} catch (Exception e) {
			logger.error("获取问题列表图片出错", e);
		}
		return list;
	}
	
	/**
	 * 获取标签
	 * @param questionId
	 * @return
	 */
	public List<String> getTagsList(int questionId){
		List<String> list = new ArrayList<>();
		try {
			if(!StringUtils.isEmpty(questionId)){
				List<QuestionTags> tagList = questionService.queryQuestionTagsList(questionId);
				for (QuestionTags tag : tagList) {
					list.add(tag.getTagName());
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		
		return list;
	}
	
	/**
	 * 获取分享
	 * @param questionId
	 * @return
	 */
	public List<Map<String, Object>> getShareList(int questionId){
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> rs = bulidMap();
		try {
			if(!StringUtils.isEmpty(questionId)){
				List<QuestionShare> shareList = questionService.queryQuestionShareList(questionId);
				for (QuestionShare questionShare : shareList) {
					rs.put("shareUrl", questionShare.getShareUrl());
					rs.put("authCode", questionShare.getAuthCode());
					list.add(rs);
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		
		return list;
	}
	
	/**
	 * 获取用户信息
	 * @param currentUserId 当前用户的id
	 * @param questionUserId 创建问题的用户id
	 * @return
	 */
	public Map<String, Object> getUserInfo(int currentUserId, int questionUserId){
		Map<String, Object> rs = this.bulidMap();
		String logo = "",nickName = "",role = "",friendShip = "",
				contrbution = "",rewardScore = "",payScore = "";
		
		rs.put("userId", currentUserId);
		
		if(!StringUtils.isEmpty(currentUserId)){
			try {
				rs.put("userId", currentUserId);
				UserAccounts user = userService.selectByPrimaryKey(currentUserId);
				logo = user.getHeadUrl();
				nickName = user.getNikeName();
				role = user.getRole();
				friendShip = "0";
				contrbution = user.getContrbution();
				rewardScore = user.getRewardScore();
				payScore = user.getPayScore();
				
				//查询当前用户和创建问题用户的关系
				if(currentUserId == questionUserId){
					friendShip = Config.TWO;
				}else{
					if(userService.isFriend(currentUserId, questionUserId)){
						friendShip = Config.ONE;
					}
				}
				
			} catch (Exception e) {
				logger.error("查询首页问题列表，获取用户信息出错", e);
			}
		}
		
		rs.put("logo", logo);
		rs.put("userId", currentUserId);
		rs.put("nickName", nickName);
		rs.put("role", role);
		rs.put("friendShip", friendShip);
		rs.put("contrbution", contrbution);
		rs.put("rewardScore", rewardScore);
		rs.put("payScore", payScore);
		
		return rs;
	}
	
	public Map<String, Object> bulidMap(){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		return resultMap;
	}
}
