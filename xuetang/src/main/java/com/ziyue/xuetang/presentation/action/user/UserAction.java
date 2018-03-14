package com.ziyue.xuetang.presentation.action.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ziyue.xuetang.model.question.QuestionInfo;
import com.ziyue.xuetang.model.user.UserAccounts;
import com.ziyue.xuetang.presentation.action.BaseAction;
import com.ziyue.xuetang.service.question.QuestionInfoService;
import com.ziyue.xuetang.service.user.UserService;
import com.ziyue.xuetang.support.question.QuestionListSupport;
import com.ziyue.xuetang.support.question.QuestionListSupportBean;
import com.ziyue.xuetang.support.question.QuestionSopport;
import com.ziyue.xuetang.support.user.UserSupport;
import com.ziyue.xuetang.utils.DESCoderUtil;
import com.ziyue.xuetang.utils.JacksonUtil;
import com.ziyue.xuetang.utils.StringUtil;

/**
 * @描述：用于用户登录和注销
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年11月12日
 * @version v1.0.
 * 
 */
@Controller
@RequestMapping("/user")
public class UserAction extends BaseAction{

private static Logger logger = Logger.getLogger(UserAction.class);
	
	@Autowired
	private UserService userService;
	
//	@Autowired
//    private FailoverJedisPool jedisPool;
	
	@Resource
	private UserSupport userSupport;
	
	@Autowired
	private QuestionInfoService questionService;
	
	@Resource
	private QuestionSopport questionSopport;
	
	@Resource
	private QuestionListSupport questionListSupport;
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @return
	 * @throws ValidateException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String login(
			@RequestBody(required = false) Map<String, Object> paramMap, HttpServletResponse response,
			HttpServletRequest request)
					throws ValidateException {
		
		logger.info("入参  ：request =  " + paramMap);
		
		Map<String, Object> resultMap = bulidResultMap();
		
		String backJson = checkParam(paramMap, new String[]{"email", "password"}, new int[]{64, 64});
		
		if(StringUtils.isEmpty(backJson)){
			//判读用户是否存在
			List<Map<String, Object>> list = userService.checkUserisRegister(paramMap);
			if(list.size() == 0){
				return retContent(RspCode.USER_STATUS_NOT_REGISTE, null);
			}
			
			//判读用户的密码与数据的是否匹配
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("email", (String)paramMap.get("email"));
			String password = DESCoderUtil.deCode(StringUtil.replaceBlank((String)paramMap.get("password")));
			map.put("password", StringUtil.replaceBlank(password));
			UserAccounts userInfo = userService.selectUser(map);
			if(userInfo == null){
				return retContent(RspCode.CODE_PARAM, "请输入正确的用户名和密码", null);
			}
			
			//判断用户绑定的板块
			resultMap.put("favSection", 1);//默认已绑定板块
			if(StringUtils.isEmpty(userInfo.getFavSectionId())){
				List<Map<String, Object>> selectResultList = geSelectList(userService);
				resultMap.put("favSection", 0);//没有绑定板块
				resultMap.put("selectList", selectResultList);
				return retContent(RspCode.SUCCESS, resultMap);
			}
			
			//判读用户的状态
			//用户状态  0初始化，1激活， 2禁止
			if(Config.ZERO.equals(userInfo.getStatus())){
				return retContent(RspCode.USER_STATUS_NOT_ACTIVED, "用户未激活", null);
			}
			if(Config.TWO.equals(userInfo.getStatus())){
				return retContent(RspCode.USER_STATUS_NOT_ALLOW_LOGIN, "该账号已禁止登陆", null);
			}
			
			resultMap.put("status", userInfo.getStatus());
			resultMap.put("role", userInfo.getRole());
			resultMap.put("usreId", userInfo.getUserId());
			resultMap.put("nickName", getNickName(userInfo.getNikeName()));
			resultMap.put("timestamp", System.currentTimeMillis() / 1000);
			
			//创建token,并把用户数据写到redis
			String token = createToken();
			resultMap.put("token", token);
			String json = "";
			try {
				json = JacksonUtil.objToJson(userInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//去除重点字段
			userInfo = userSupport.removeFields(userInfo);
			resultMap.put("userInfo", userInfo);
			if(saveToken(token, resultMap)){
				//redis缓存成功，更新登录数据
				UserAccounts userNew = new UserAccounts();
				String IP = request.getRemoteAddr();
				
				logger.info("用户： " + paramMap.get("email") + "登录IP：" + IP);
				
				userNew.setId(userInfo.getId());
				userNew.setLastLoginIp(IP);
				userNew.setLastLoginTime((System.currentTimeMillis() / 1000) + "");
				userService.updateByPrimaryKeySelective(userNew);
				
				return retContent(RspCode.SUCCESS, resultMap);
			}
			logger.info("用户 usreId = " + userInfo.getUserId() + " redis写入失败");
			return retContent(RspCode.CODE_SERVER_INTERNAL, null);
		}
		
		return backJson;
	}
	
	
	public String getNickName(String nickName){
		if(StringUtils.isEmpty(nickName)){
			return "子曰用户";
		}
		return nickName;
	}
	
	public static List<Map<String, Object>> geSelectList(UserService userService){
		List<Map<String, Object>> selectList = userService.getSelectList();
		List<Map<String, Object>> boradsList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> selectResultList = new ArrayList<Map<String, Object>>();
		if(selectList.size() != 0){
			for (Map<String, Object> selectMap : selectList) {
				Map<String, Object> rs = new HashMap<String, Object>();
				boradsList = userService.getBoradsList(String.valueOf(selectMap.get("id")));
				rs.put("boradsList",boradsList);
				rs.put("selectName",selectMap.get("title"));
				selectResultList.add(rs);
			}
		}
		return selectResultList;
	}
	
	
	/**
	 * 根据userId获取用户的基本信息
	 */
	@RequestMapping(value = "/basicinfo", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String getBasicInfo(
			@RequestBody(required = false) Map<String, Object> paramMap, HttpServletResponse response,
			HttpServletRequest request)
					throws ValidateException {
		
		paramMap = getParamsByGet(request);
		logger.info("入参  ：request =  " + paramMap);
		
		checkParam(paramMap, new String[]{"uid"}, new int[]{10});
		
		int uid = Integer.valueOf(String.valueOf(paramMap.get("uid")));
		int userId = Integer.valueOf(String.valueOf(paramMap.get("userId")));
		
		UserAccounts user = userService.selectByPrimaryKey(uid);
		
		if(user == null){
			return retContent(RspCode.CODE_PARAM, "用户不存在", null);
		}
		
		String logo = "",nickName = "",role = "",friendShip = "",
				contrbution = "",rewardScore = "",payScore = "";
		
		logo = user.getHeadUrl();
		nickName = user.getNikeName();
		role = user.getRole();
		friendShip = "0";
		contrbution = user.getContrbution();
		rewardScore = user.getRewardScore();
		payScore = user.getPayScore();
		
		//查询当前用户和创建问题用户的关系
		if(uid == userId){
			friendShip = Config.TWO;
		}else{
			if(userService.isFriend(userId, uid)){
				friendShip = Config.ONE;
			}
		}
		
		Map<String, Object> rs = bulidMap();
		rs.put("logo", logo);
		rs.put("nickName", nickName);
		rs.put("role", role);
		rs.put("contrbution", contrbution);
		rs.put("rewardScore", rewardScore);
		rs.put("payScore", payScore);
		rs.put("friendShip", friendShip);
		rs.put("userId", uid);
		
		return retContent(RspCode.SUCCESS, rs);
	}
	
	
	/**
	 * 收藏 、 取消收藏
	 */
	@RequestMapping(value = "/collections_question", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	public @ResponseBody String collectionsQuestion(
			@RequestBody(required = false) Map<String, Object> paramMap, HttpServletResponse response,
			HttpServletRequest request)throws ValidateException{
		
		logger.info("入参  ：request =  " + paramMap);
		
		checkToken(paramMap);
		
		checkParam(paramMap, new String[]{"questionId", "flag"}, new int[]{10, 10});
		
		
		String flag = StringUtil.getStringValue(paramMap, "flag");
		
		//0 采纳
		if(flag.equals(Config.ZERO)){
			
			userSupport.collectionQuestion(paramMap);
			
		//1 取消
		}else if(flag.equals(Config.ONE)){
			
			userSupport.collectionQuestion(paramMap);
			
		}else{
			return retContent(RspCode.CODE_PARAM, "flag必须为0或者1", null);
		}
		
		return retContent(RspCode.SUCCESS, null);
	}
	
	/**
	 * 更新、修改
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	public @ResponseBody String update(
			@RequestBody(required = false) Map<String, Object> paramMap, HttpServletResponse response,
			HttpServletRequest request)throws ValidateException{
		
		logger.info("入参  ：request =  " + paramMap);
		
		checkToken(paramMap);
		
		checkParam(paramMap, new String[]{"nickName", "sex", "wx", "school", "qq", "boardId"}, new int[]{32, 4, 10, 15, 10, 4});
		
		userSupport.update(paramMap);
		
		return retContent(RspCode.SUCCESS, null);
	}
	
	/**
	 * 更新密码（内部页面）
	 */
	@RequestMapping(value = "/update_password", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	public @ResponseBody String updatePassword(
			@RequestBody(required = false) Map<String, Object> paramMap, HttpServletResponse response,
			HttpServletRequest request)throws ValidateException{
		
		logger.info("入参  ：request =  " + paramMap);
		
		checkToken(paramMap);
		
		checkParam(paramMap, new String[]{"password", "newPwd"}, new int[]{32, 32});
		
		userSupport.updatePassword(paramMap);
		
		return retContent(RspCode.SUCCESS, null);
	}
	
	/**
	 * 更新头像
	 */
	@RequestMapping(value = "/update_logo", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	public @ResponseBody String updateLogo(
			@RequestBody(required = false) Map<String, Object> paramMap, HttpServletResponse response,
			HttpServletRequest request)throws ValidateException{
		
		logger.info("入参  ：request =  " + paramMap);
		
		checkToken(paramMap);
		
		checkParam(paramMap, new String[]{"url"}, new int[]{256});
		
		userSupport.updateLogo(paramMap);
		
		return retContent(RspCode.SUCCESS, null);
	}
	
	/**
	 * 个人中心 - 我的提问列表
	 */
	@RequestMapping(value = "/center/my_question_list", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
	public @ResponseBody String myQuestionList(
			@RequestBody(required = false) Map<String, Object> paramMap, HttpServletResponse response,
			HttpServletRequest request)throws ValidateException{
		
		paramMap = getParamsByGet(request);
		logger.info("入参  ：request =  " + paramMap);
		
		checkToken(paramMap);
		
		int currentUserId = Integer.valueOf(request.getParameter("userId"));
		
		//获取分页信息
		int pageNo = PageUtil.getDefualtPageNo(paramMap);
		int pageSize = PageUtil.getDefualtPageSize(paramMap);
		
		try {
			//未删除
			paramMap.put("isDelete", Config.ZERO);
			//按创建时间排序
			paramMap.put("query", Config.THREE);
			//创建用户的id
			paramMap.put("userId", StringUtil.getIntValue(paramMap, "userId"));
			
			//根据分页信息查询列表
			PageInfo<QuestionInfo> question = questionService.queryQuestionList(paramMap, pageNo, pageSize);
//			List<Map<String, Object>> list = getIndexList(question);
			QuestionListSupportBean bean = new QuestionListSupportBean();
			bean.setCurrentUserId(currentUserId);
			List<Map<String, Object>> list = questionListSupport.getQuestionList(Config.QUESTION_CREATE_LIST, question, bean);
			//返回结果集
			Map<String, Object> resultMap = bulidResultMap();
			
			resultMap.put("dataList", list);
			resultMap.put("pageNo", pageNo);
			resultMap.put("pageSize", pageSize);
			resultMap.put("tatal", question.getTotal());
			
			return retContent(RspCode.SUCCESS, resultMap);
			
		} catch (Exception e) {
			logger.error("查询个人中心，我的提问问题列表出错", e);
			return retContent(RspCode.CODE_SERVER_INTERNAL, null);
			
		}
		
	}
	
	/**
	 * 个人中心 - 我参与回答的问题列表
	 */
	@RequestMapping(value = "/center/my_answer_question_list", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
	public @ResponseBody String myAnswerQuestionList(
			@RequestBody(required = false) Map<String, Object> paramMap, HttpServletResponse response,
			HttpServletRequest request)throws ValidateException{
		
		paramMap = getParamsByGet(request);
		logger.info("入参  ：request =  " + paramMap);
		
		checkToken(paramMap);
		
		int currentUserId = Integer.valueOf(request.getParameter("userId"));
		
		//获取分页信息
		int pageNo = PageUtil.getDefualtPageNo(paramMap);
		int pageSize = PageUtil.getDefualtPageSize(paramMap);
		
		try {
			//未删除
			paramMap.put("isDelete", Config.ZERO);
			//按创建时间排序
			paramMap.put("query", Config.THREE);
			//创建用户的id
			paramMap.put("userId", StringUtil.getIntValue(paramMap, "userId"));
			
			//根据分页信息查询列表
			PageInfo<QuestionInfo> question = questionService.queryMyAnswerQuestionList(paramMap, pageNo, pageSize);
//			List<Map<String, Object>> list = getIndexList(question);
			QuestionListSupportBean bean = new QuestionListSupportBean();
			bean.setCurrentUserId(currentUserId);
			List<Map<String, Object>> list = questionListSupport.getQuestionList(Config.QUESTION_ANSWER_LIST, question, bean);
			//返回结果集
			Map<String, Object> resultMap = bulidResultMap();
			
			resultMap.put("dataList", list);
			resultMap.put("pageNo", pageNo);
			resultMap.put("pageSize", pageSize);
			resultMap.put("tatal", question.getTotal());
			
			return retContent(RspCode.SUCCESS, resultMap);
			
		} catch (Exception e) {
			logger.error("查询个人中心，我参与的问题列表出错", e);
			return retContent(RspCode.CODE_SERVER_INTERNAL, null);
			
		}
		
	}	
	
	/**
	 * 个人中心 - 我收藏的问题列表
	 */
	@RequestMapping(value = "/center/my_collection_question_list", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
	public @ResponseBody String myCollectionQuestionList(
			@RequestBody(required = false) Map<String, Object> paramMap, HttpServletResponse response,
			HttpServletRequest request)throws ValidateException{
		
		paramMap = getParamsByGet(request);
		logger.info("入参  ：request =  " + paramMap);
		
		checkToken(paramMap);
		
		int currentUserId = Integer.valueOf(request.getParameter("userId"));
		
		//获取分页信息
		int pageNo = PageUtil.getDefualtPageNo(paramMap);
		int pageSize = PageUtil.getDefualtPageSize(paramMap);
		
		try {
			//未删除
			paramMap.put("isDelete", Config.ZERO);
			//按创建时间排序
			paramMap.put("query", Config.THREE);
			//创建用户的id
			paramMap.put("userId", StringUtil.getIntValue(paramMap, "userId"));
			
			//根据分页信息查询列表
			PageInfo<QuestionInfo> question = questionService.queryMyCollectionQuestionList(paramMap, pageNo, pageSize);
//			List<Map<String, Object>> list = getIndexList(question);
			QuestionListSupportBean bean = new QuestionListSupportBean();
			bean.setCurrentUserId(currentUserId);
			List<Map<String, Object>> list = questionListSupport.getQuestionList(Config.QUESTION_ANSWER_LIST, question, bean);
			//返回结果集
			Map<String, Object> resultMap = bulidResultMap();
			
			resultMap.put("dataList", list);
			resultMap.put("pageNo", pageNo);
			resultMap.put("pageSize", pageSize);
			resultMap.put("tatal", question.getTotal());
			
			return retContent(RspCode.SUCCESS, resultMap);
			
		} catch (Exception e) {
			logger.error("查询个人中心，我收藏的问题列表出错", e);
			return retContent(RspCode.CODE_SERVER_INTERNAL, null);
			
		}
		
	}	
	
	/**
	 * 个人中心 - 举报的问题列表
	 */
	@RequestMapping(value = "/center/report_question_list", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
	public @ResponseBody String reportQuestionList(
			@RequestBody(required = false) Map<String, Object> paramMap, HttpServletResponse response,
			HttpServletRequest request)throws ValidateException{
		
		paramMap = getParamsByGet(request);
		logger.info("入参  ：request =  " + paramMap);
		
		checkToken(paramMap);
		
		int currentUserId = Integer.valueOf(request.getParameter("userId"));
		
		//获取分页信息
		int pageNo = PageUtil.getDefualtPageNo(paramMap);
		int pageSize = PageUtil.getDefualtPageSize(paramMap);
		
		try {
			//未删除
			paramMap.put("isDelete", Config.ZERO);
			//按创建时间排序
			paramMap.put("query", Config.THREE);
			//创建用户的id
			paramMap.put("userId", StringUtil.getIntValue(paramMap, "userId"));
			
			UserAccounts user = userService.selectUserByUserId(StringUtil.getIntValue(paramMap, "userId"));
			
			
			if(!user.getRole().equals(Config.ONE)){
				return retContent(RspCode.USER_STATUS_NOT_QUERY_REPORT_AUTH, null);
			}
			//用户所属领域
			paramMap.put("boardId", user.getFavSectionId());
			
			//根据分页信息查询列表
			PageInfo<QuestionInfo> question = questionService.queryReportQuestionList(paramMap, pageNo, pageSize);
//			List<Map<String, Object>> list = getIndexList(question);
			QuestionListSupportBean bean = new QuestionListSupportBean();
			bean.setCurrentUserId(currentUserId);
			List<Map<String, Object>> list = questionListSupport.getQuestionList(Config.QUESTION_ANSWER_LIST, question, bean);
			//返回结果集
			Map<String, Object> resultMap = bulidResultMap();
			
			resultMap.put("dataList", list);
			resultMap.put("pageNo", pageNo);
			resultMap.put("pageSize", pageSize);
			resultMap.put("tatal", question.getTotal());
			
			return retContent(RspCode.SUCCESS, resultMap);
			
		} catch (Exception e) {
			logger.error("查询个人中心，我参与的问题列表出错", e);
			return retContent(RspCode.CODE_SERVER_INTERNAL, null);
			
		}
		
	}
	
	/**
	 * 获取用户绑定板块名称
	 * @param paramMap
	 * @param response
	 * @return
	 * @throws ValidateException
	 */
	@RequestMapping(value = "/getBoardName", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String login(
			@RequestBody(required = false) Map<String, Object> paramMap, HttpServletResponse response)
					throws ValidateException {
		
		logger.info("入参  ：request =  " + paramMap);
		
		checkToken(paramMap);
		
		checkParam(paramMap, new String[]{"userId"}, new int[]{10});
		
		Map<String, Object> rs = userService.getUserBoards(paramMap);
		
		Map<String, Object> resultMap = bulidMap();
		resultMap.put("boardName", rs.get("title"));
		
		
		return retContent(RspCode.SUCCESS, resultMap);
	}
	
}
