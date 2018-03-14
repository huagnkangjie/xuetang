package com.ziyue.xuetang.presentation.action.question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.ziyue.xuetang.common.page.Page;
import com.ziyue.xuetang.common.page.PageUtil;
import com.ziyue.xuetang.constant.Config;
import com.ziyue.xuetang.constant.RspCode;
import com.ziyue.xuetang.exception.ValidateException;
import com.ziyue.xuetang.model.question.QuestionAnswer;
import com.ziyue.xuetang.model.question.QuestionInfo;
import com.ziyue.xuetang.model.question.QuestionShare;
import com.ziyue.xuetang.model.question.QuestionTags;
import com.ziyue.xuetang.model.user.UserAccounts;
import com.ziyue.xuetang.model.user.UserCollectionsQuestion;
import com.ziyue.xuetang.presentation.action.BaseAction;
import com.ziyue.xuetang.service.question.QuestionInfoService;
import com.ziyue.xuetang.service.user.UserService;
import com.ziyue.xuetang.support.question.QuestionListSupport;
import com.ziyue.xuetang.support.question.QuestionListSupportBean;
import com.ziyue.xuetang.support.question.QuestionSopport;
import com.ziyue.xuetang.utils.JacksonUtil;
import com.ziyue.xuetang.utils.StringUtil;
import com.ziyue.xuetang.utils.TimeUtil;

/**
 * @描述：	提问管理类
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年11月25日
 * @version v1.0.
 * 
 */
@Controller
@RequestMapping("/question")
public class QuestionAction extends BaseAction {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QuestionInfoService questionService;
	
	@Resource
	private QuestionSopport questionSopport;
	
	@Resource
	private QuestionListSupport questionListSupport;
	
	private static Logger logger = Logger.getLogger(QuestionAction.class);
	
	private int currentUserId = 0;
	
	
	/**
	 * 添加问题
	 * @param paramMap
	 * @param response
	 * @return
	 * @throws ValidateException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String addQuestion(
			@RequestBody(required = false) Map<String, Object> paramMap, HttpServletResponse response)
					throws ValidateException {
		
		logger.info("入参  ：request =  " + paramMap);
		
		checkToken(paramMap);
		
		checkParam(paramMap, new String[]{"title", "tagIds", "rewardCount", "shareUrls"}, new int[]{50, 100, 32, 1024});
		
		List<String> pictList = new ArrayList<>();
		//添加图片
		if(!StringUtils.isEmpty(paramMap.get("picList"))){
			pictList = (List<String>) paramMap.get("picList");
		}
		
		List<Object> tags = null;
		List<Map<String, String>> shareUrls = null;
		try {
			tags = (List<Object>) paramMap.get("tagIds");
		} catch (Exception e) {
			return retContent(RspCode.CODE_PARAM, "tagIds参数不合法", null);
		}
		
		try {
			shareUrls = (List<Map<String, String>>) paramMap.get("shareUrls");
		} catch (Exception e) {
			return retContent(RspCode.CODE_PARAM, "shareUrls参数不合法", null);
		}
		
		checkQuestionParm(paramMap, tags, shareUrls);
		
		try {
			
			QuestionInfo question = this.buildQuestionInfo(paramMap);
			
			questionService.insertSelective(question);
			
			insertTag(tags, question.getId());
			insertShare(shareUrls, question.getId());
			
			//添加图片
			questionSopport.insertQuestionPic(pictList, question.getId(), question.getUserId());
			
		} catch (Exception e) {
			logger.error("添加问题出错", e);
			return retContent(RspCode.CODE_SERVER_INTERNAL, null);
			
		}
		
		return retContent(RspCode.SUCCESS, null);
	}
	
	/**
	 * 检查创建问题的参数
	 * @param paramMap
	 * @param tags
	 * @param shareUrls
	 */
	public void checkQuestionParm(Map<String, Object> paramMap, List<Object> tags,
			List<Map<String, String>> shareUrls){
		if(StringUtils.isEmpty(paramMap.get("content")) ||  
				StringUtils.isEmpty(paramMap.get("markdownCont"))){
			throw new ValidateException(RspCode.CODE_PARAM, "提问内容不能为空");
		}
		
		if(tags.size() == 0 || tags.size() > 5){
			throw new ValidateException(RspCode.CODE_PARAM, "最多只能选5个标签");
		}
		
		if(shareUrls.size() > 3){
			throw new ValidateException(RspCode.CODE_PARAM, "最多只能添加3个分享");
		}
	}
	
	/**
	 * 构建QuestionInfo对象
	 * @param paramMap
	 * @return
	 * @throws ValidateException
	 */
	public QuestionInfo buildQuestionInfo(Map<String, Object> paramMap) throws ValidateException{
		QuestionInfo question = null;
		try {
			UserAccounts userInfo = userService.selectUser(paramMap);
			question = (QuestionInfo) JacksonUtil.jsonToObj(
					JacksonUtil.mapToJson(paramMap), QuestionInfo.class);
			
			question.setBoardId(Integer.valueOf(userInfo.getFavSectionId()));
			question.setType(Config.ZERO);
			question.setStatus(Config.ZERO);
			question.setAnswerCount(0);
			question.setIsDelete(Config.ZERO);
			question.setReportType(Config.ZERO);
			question.setCreateTime(TimeUtil.currentTimeMillisStr());
		} catch (InstantiationException | IllegalAccessException | IOException e) {
			logger.error("转换json出错", e);
		}
		
		return question;
	}
	
	/**
	 * 插入标签
	 * @param tags
	 * @param questionId
	 */
	public void insertTag(List<Object> tags, int questionId){
		for (Object tag : tags) {
			QuestionTags qtag = new QuestionTags();
			qtag.setQuestionId(questionId);
			qtag.setTagId(Integer.valueOf(String.valueOf(tag)));
			
			questionService.insertTag(qtag);
			
		}
	}
	
	/**
	 * 插入分享url
	 * @param shareUrls
	 * @param questionId
	 */
	public void insertShare(List<Map<String, String>> shareUrls, int questionId){
		for (Map<String, String> shareMap : shareUrls) {
			QuestionShare qshare = new QuestionShare();
			qshare.setQuestionId(questionId);
			qshare.setShareUrl(shareMap.get("shareUrl"));
			qshare.setAuthCode(shareMap.get("authCode"));
			
			questionService.inserShareUrl(qshare);
		}
	}
	
	
	/**
	 * 删除问题
	 * @param paramMap
	 * @param response
	 * @return
	 * @throws ValidateException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	public @ResponseBody String deleteQuestion(
			@RequestBody(required = false) Map<String, Object> paramMap, HttpServletResponse response)
					throws ValidateException {
		
		logger.info("入参  ：request =  " + paramMap);
		
		checkToken(paramMap);
		
		checkParam(paramMap, new String[]{"questionId"}, new int[]{10});
		
		try {
			
			int i = questionService.deleteByPrimaryKey(
					Integer.valueOf(
							String.valueOf(paramMap.get("questionId"))));
			
			if(i == 0){
				return retContent(RspCode.CODE_PARAM, "question not exsit", null);
			}
			
		} catch (Exception e) {
			logger.error("删除问题出错", e);
			return retContent(RspCode.CODE_SERVER_INTERNAL, null);
			
		}
		
		return retContent(RspCode.SUCCESS, null);
	}
	
	
	/**
	 * 问题列表 -- 首页列表
	 * @param paramMap
	 * @param response
	 * @return
	 * @throws ValidateException
	 * @throws ServletException 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
	public @ResponseBody String questionList(
			@RequestBody(required = false) Map<String, Object> paramMap, HttpServletResponse response,
			HttpServletRequest request)throws ValidateException, IOException, ServletException {
		
		paramMap = getParamsByGet(request);
		logger.info("入参  ：request =  " + paramMap);
		
		checkToken(paramMap);
		
		//query 0:悬赏	1:未答	2:采纳	3:时间
		checkParam(paramMap, new String[]{"query", "tagId"}, new int[]{10, 10});
		
		currentUserId = Integer.valueOf(request.getParameter("userId"));
		
		if(String.valueOf(paramMap.get("tagId")).equals(Config.ONE)){
			paramMap.remove("tagId");
		}
		
		//获取分页信息
		int pageNo = PageUtil.getDefualtPageNo(paramMap);
		int pageSize = PageUtil.getDefualtPageSize(paramMap);
		
		try {
			//未删除
			paramMap.put("isDelete", Config.ZERO);
			//判断标签，全部为0
			if(String.valueOf(paramMap.get("tagId")).equals(Config.ZERO)){
				//如果标签不为0则作为条件查询
				paramMap.remove("tagId");
				logger.info("查询条件tagId为全部");
			}
			
			//根据分页信息查询列表
			PageInfo<QuestionInfo> question = questionService.queryQuestionList(paramMap, pageNo, pageSize);
//			List<Map<String, Object>> list = getIndexList(question);
			QuestionListSupportBean bean = new QuestionListSupportBean();
			bean.setCurrentUserId(this.currentUserId);
			List<Map<String, Object>> list = questionListSupport.getQuestionList(Config.QUESTION_INDEX_LIST, question, bean);
			//返回结果集
			Map<String, Object> resultMap = bulidResultMap();
			
			resultMap.put("dataList", list);
			resultMap.put("pageNo", pageNo);
			resultMap.put("pageSize", pageSize);
			resultMap.put("tatal", question.getTotal());
			
			return retContent(RspCode.SUCCESS, resultMap);
			
		} catch (Exception e) {
			logger.error("查询首页问题列表出错", e);
			return retContent(RspCode.CODE_SERVER_INTERNAL, null);
			
		}
		
	}
	
	
	/**
	 * 获取问题详情
	 * @param paramMap
	 * @param response
	 * @param request
	 * @return
	 * @throws ValidateException
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
	public @ResponseBody String questionDetail(
			@RequestBody(required = false) Map<String, Object> paramMap, HttpServletResponse response,
			HttpServletRequest request)throws ValidateException, IOException, ServletException {
		
		paramMap = getParamsByGet(request);
		logger.info("入参  ：request =  " + paramMap);
		
		checkToken(paramMap);
		
		checkParam(paramMap, new String[]{"questionId"}, new int[]{10});
		
		QuestionInfo question = questionService.selectByPrimaryKey(
				Integer.valueOf(String.valueOf(paramMap.get("questionId"))));
		if(question != null){
			
			Map<String, Object> rs = questionListSupport.getQuestion(question);
			String nickName = "";
			try {
				UserAccounts user = userService.selectByPrimaryKey(question.getUserId());
				nickName = user.getNikeName();
			} catch (Exception e) {
				logger.error("查看用户详情，查询用户出错", e);
			}
			
			rs.put("nickName", nickName);
			
			//查询采纳情况
			if(!StringUtils.isEmpty(question.getAdoptAnswerId())){
				rs.put("adopt", getAdopt(question));
			}
			
			return retContent(RspCode.SUCCESS, rs);
		}
		
		return retContent(RspCode.CODE_PARAM, "问题不存在", null);
	}
	
	/**
	 * 获取问题采纳的信息
	 * @param question
	 * @return
	 */
	public Map<String, Object> getAdopt(QuestionInfo question){
		Map<String, Object> rs = bulidMap();
		if(!StringUtils.isEmpty(question.getAdoptUserId())){
			
			//获取用户信息
			UserAccounts userInfo = userService.selectByPrimaryKey(
					question.getAdoptUserId());
			rs.put("nickName", userInfo.getNikeName());
			rs.put("userId", userInfo.getId());
			rs.put("logo", userInfo.getHeadUrl());
			rs.put("createTime", TimeUtil.getTimeByMillis(
					Long.valueOf(question.getFristAnswerTime() + "000")));
			
			rs.put("content", "content");
			rs.put("markdownCont", "markdownCont");
			rs.put("content", "content");
			rs.put("status", 1);
		}
		return rs;
	}
	
	
	/**
	 * 获取问题回答的列表
	 * @param paramMap
	 * @param response
	 * @param request
	 * @return
	 * @throws ValidateException
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping(value = "/answer_list", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
	public @ResponseBody String answerList(
			@RequestBody(required = false) Map<String, Object> paramMap, HttpServletResponse response,
			HttpServletRequest request)throws ValidateException, IOException, ServletException {
		
		paramMap = getParamsByGet(request);
		logger.info("入参  ：request =  " + paramMap);
		
		checkToken(paramMap);
		
		checkParam(paramMap, new String[]{"queryParams", "pageNo", "pageSize"}, new int[]{10, 3, 3});
		
		
		//获取分页信息
		int pageNo = PageUtil.getDefualtPageNo(paramMap);
		int pageSize = PageUtil.getDefualtPageSize(paramMap);
		paramMap = questionSopport.checkParams(paramMap);
		PageInfo<Map<String,Object>> pageInfo = questionService.querQuestionAnswerLevelOne(paramMap, pageNo, pageSize);
		List<Map<String, Object>> answerList = questionSopport.getAnswerList(pageInfo);
 		
		Map<String, Object> rs = bulidMap();
		rs.put("anserList", answerList);
		rs.put(Page.PAGE_NO, pageNo);
		rs.put(Page.PAGE_SIZE, pageSize);
		rs.put(Page.TOTAL, pageInfo.getTotal());
		
		return retContent(RspCode.SUCCESS, rs);
	}
	
	/**
	 * 回复问题 - 一级回复
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/answer_level_one", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	public @ResponseBody String answerLevelOne(
			@RequestBody(required = false) Map<String, Object> paramMap, HttpServletResponse response,
			HttpServletRequest request)throws ValidateException{
		
		logger.info("入参  ：request =  " + paramMap);
		
		checkToken(paramMap);
		
		checkParam(paramMap, new String[]{"questionId", "content", "markdownCont"}, new int[]{10, 1024, 2048});
		
		List<String> picList = new ArrayList<>();
		//如果图片不为空的话则保存图片
		if(!StringUtils.isEmpty(paramMap.get("picList"))){
			picList = (List<String>) paramMap.get("picList");
		}
		
		QuestionAnswer answer = new QuestionAnswer();
		//添加评论
		try {
			answer = (QuestionAnswer) JacksonUtil.mapToBean(paramMap, QuestionAnswer.class);
//			answer = (QuestionAnswer) JacksonUtil.jsonToObj(JacksonUtil.mapToJson(paramMap), QuestionAnswer.class);
		} catch (Exception e) {
			logger.error("转换QuestionAnswer bean 出错", e);
			return retContent(RspCode.CODE_SERVER_INTERNAL, null);
		}
		
		answer.setCreateTime(TimeUtil.currentTimeMillisStr());
		answer.setIsDelete(Config.ZERO);
		answer.setStatus(Config.ZERO);
		questionService.insertQuestionAnswer(answer);
		//添加评论的图片
		if(picList.size() > 0){
			int userId = StringUtil.getIntValue(paramMap, "userId");
			questionSopport.insertQuestionAnswerPic(picList, answer.getId(), userId);
		}
		
		return retContent(RspCode.SUCCESS, null);
	}
	/**
	 * 回复问题  , 二级回复
	 */
	@RequestMapping(value = "/answer_level_two", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	public @ResponseBody String answerLevelTwo(
			@RequestBody(required = false) Map<String, Object> paramMap, HttpServletResponse response,
			HttpServletRequest request)throws ValidateException{
		
		logger.info("入参  ：request =  " + paramMap);
		
		checkToken(paramMap);
		
		checkParam(paramMap, new String[]{"questionId", "content", "markdownCont","answerLevelOneId","toUserId"}, new int[]{10, 1024, 2048, 10, 10});
		
		QuestionAnswer answer = new QuestionAnswer();
		//添加评论
		try {
			answer = (QuestionAnswer) JacksonUtil.mapToBean(paramMap, QuestionAnswer.class);
//			answer = (QuestionAnswer) JacksonUtil.jsonToObj(JacksonUtil.mapToJson(paramMap), QuestionAnswer.class);
		} catch (Exception e) {
			logger.error("转换QuestionAnswer bean 出错", e);
			return retContent(RspCode.CODE_SERVER_INTERNAL, null);
		}
		
		answer.setCreateTime(TimeUtil.currentTimeMillisStr());
		answer.setIsDelete(Config.ZERO);
		answer.setStatus(Config.ZERO);
		answer.setFid(StringUtil.getIntValue(paramMap, "answerLevelOneId"));
		questionService.insertQuestionAnswer(answer);
		
		return retContent(RspCode.SUCCESS, null);
	}
	
	
	/**
	 * 采纳、取消采纳
	 */
	@RequestMapping(value = "/adopt", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	public @ResponseBody String adopt(
			@RequestBody(required = false) Map<String, Object> paramMap, HttpServletResponse response,
			HttpServletRequest request)throws ValidateException{
		
		logger.info("入参  ：request =  " + paramMap);
		
		checkToken(paramMap);
		
		checkParam(paramMap, new String[]{"questionId", "answerId", "flag", "answerUserId"}, new int[]{10, 10, 10, 10});
		
		
		String flag = StringUtil.getStringValue(paramMap, "flag");
		
		//0 采纳
		if(flag.equals(Config.ZERO)){
			
			questionSopport.adpot(paramMap);
			
		//1 取消
		}else if(flag.equals(Config.ONE)){
			
			questionSopport.cancelAdopt(paramMap);
			
		}else{
			return retContent(RspCode.CODE_PARAM, "flag必须为0或者1", null);
		}
		
		return retContent(RspCode.SUCCESS, null);
	}
	
	
	/**
	 * 举报
	 */
	@RequestMapping(value = "/reports", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	public @ResponseBody String reports(
			@RequestBody(required = false) Map<String, Object> paramMap, HttpServletResponse response,
			HttpServletRequest request)throws ValidateException{
		
		logger.info("入参  ：request =  " + paramMap);
		
		checkToken(paramMap);
		
		checkParam(paramMap, new String[]{"questionId", "flag", "type"}, new int[]{10, 10, 10});
		
		questionSopport.reports(paramMap);
		
		return retContent(RspCode.SUCCESS, null);
	}
	
	
	
	
}
