package com.ziyue.xuetang.presentation.action.user;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziyue.xuetang.constant.Config;
import com.ziyue.xuetang.constant.RspCode;
import com.ziyue.xuetang.exception.ValidateException;
import com.ziyue.xuetang.model.user.UserAccounts;
import com.ziyue.xuetang.presentation.action.BaseAction;
import com.ziyue.xuetang.service.user.UserService;
import com.ziyue.xuetang.utils.DESCoderUtil;
import com.ziyue.xuetang.utils.FastJsonUtil;
import com.ziyue.xuetang.utils.JacksonUtil;
import com.ziyue.xuetang.utils.PropUtil;
import com.ziyue.xuetang.utils.RequestUtil;
import com.ziyue.xuetang.utils.StringUtil;
import com.ziyue.xuetang.utils.TimeUtil;
import com.ziyue.xuetang.utils.ValidateUtil;
import com.ziyue.xuetang.utils.mail.MailVo;
import com.ziyue.xuetang.utils.mail.SendMailManager;

/**
 * @描述：用户注册信息管理类
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年11月6日
 * @version v1.0.
 * 
 */

@Controller
@RequestMapping("/user/register")
public class UserRegisterAction extends BaseAction{
	
	private static Logger logger = Logger.getLogger(UserRegisterAction.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * 判断邮箱是否唯一性
	 * @param request
	 * @param response
	 * @return
	 * @throws ValidateException
	 */
	@RequestMapping(value = "/check_register", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String checkRegister(
			@RequestBody(required = false) Map<String, Object> request, HttpServletResponse response)
					throws ValidateException {
		
		logger.info("入参  ：request =  " + request);
		
		Map<String, Object> resultMap = bulidResultMap();
		
		String backJson = checkParam(request, new String[]{"email"}, new int[]{64});
		
		if(StringUtils.isEmpty(backJson)){
			
			boolean flag = ValidateUtil.validataEmaile((String)request.get("email"));
			if(!flag){
				return retContent(RspCode.CODE_PARAM, "邮箱格式错误", null);
			}
			
			try {
				int i = 1;//已注册
				List<Map<String, Object>> list = userService.checkUserisRegister(request);
				if(list.size() == 0){
					i = 0;
				}
				resultMap.put("isRegistered", i);
				return retContent(RspCode.SUCCESS, resultMap);
				
			} catch (Exception e) {
				logger.error("注册校验邮箱唯一性错误", e);
				return retContent(RspCode.CODE_SERVER_INTERNAL, null);
			}
		}
		
		return "";
	}
	
	/**
	 * 发送注册邮件
	 * @param request
	 * @param response
	 * @return
	 * @throws ValidateException
	 */
	@RequestMapping(value = "/send_mail", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String sendMail(
			@RequestBody(required = false) Map<String, Object> request, HttpServletResponse response)
					throws ValidateException {
		
		logger.info("入参  ：request =  " + request);
		
		Map<String, Object> resultMap = bulidResultMap();
		
		String backJson = checkParam(request, new String[]{"email"}, new int[]{64});
		
		if(StringUtils.isEmpty(backJson)){
			try {
				int isRegistered = 1; //已注册
				List<Map<String, Object>> list = userService.checkUserisRegister(request);
				if(list.size() == 0){
					
					String email = String.valueOf(request.get("email"));
					isRegistered = 0;
					MailVo mail = new MailVo();
					mail.setReceiver(email);
					mail.setSubject("子曰学堂  - 注册验证码");
					
					String authCode = Config.getRandomString(6);
					
					String template = new PropUtil().getValue("regesiter.template");
					template = MessageFormat.format(template, authCode);
					
					mail.setContent(template);
					boolean flag = SendMailManager.sendMail(mail);
					
					if(flag){
						/*int maxUserId = userService.getMaxUserId();
						
						UserAccounts user = new UserAccounts();
						user.setUserId(Integer.valueOf(maxUserId) + 1);
						user.setEmail(email);
						user.setAuthCode(authCode);
						user.setCreateTime(TimeUtil.currentTimeMillisStr());
						user.setRole(Config.ZERO);
						user.setStatus(Config.ZERO);
						
						userService.insertSelective(user);*/
						//写入reids
						String redisRegisterEmail = Config.REDIS_USER_REGISTER_EMAIL + email;
						
						Map<String, String> map = new HashMap<String,String>();
						map.put("email", email);
						map.put("authCode", authCode);
						jedisPool.getJedis().set(redisRegisterEmail, JacksonUtil.mapToJson(map));
						jedisPool.getJedis().expire(redisRegisterEmail, 30 * 60);
					}else{
						
						return retContent(RspCode.CODE_SEND_MAIL_FAIL, "邮件发送失败，请稍后再试", null);
					}
				}
				
				resultMap.put("isRegistered", isRegistered);
				
				return retContent(RspCode.SUCCESS, resultMap);
				
			} catch (Exception e) {
				logger.error("注册校验邮箱唯一性错误", e);
				return retContent(RspCode.CODE_SERVER_INTERNAL, null);
			}
			
			
		}
		
		return "";
	}
	
	/**
	 * 发送忘记密码邮件
	 * @param request
	 * @param response
	 * @return
	 * @throws ValidateException
	 */
	@RequestMapping(value = "/send_forget_mail", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String sendForgetMail(
			@RequestBody(required = false) Map<String, Object> request, HttpServletResponse response)
					throws ValidateException {
		
		logger.info("入参  ：request =  " + request);
		
		Map<String, Object> resultMap = bulidResultMap();
		
		String backJson = checkParam(request, new String[]{"email"}, new int[]{64});
		
		if(StringUtils.isEmpty(backJson)){
			try {
				List<Map<String, Object>> list = userService.checkUserisRegister(request);
				if(list.size() == 1){
					
					String email = String.valueOf(request.get("email"));
					MailVo mail = new MailVo();
					mail.setReceiver(email);
					mail.setSubject("子曰学堂  - 忘记密码验证码");
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("email", email);
					UserAccounts user = userService.selectUser(param);
					String authCode = user.getAuthCode();
					
					String template = new PropUtil().getValue("forget.password.template");
					template = MessageFormat.format(template, authCode);
					
					mail.setContent(template);
					boolean flag = SendMailManager.sendMail(mail);
					
					if(!flag){
						return retContent(RspCode.CODE_SEND_MAIL_FAIL, "邮件发送失败，请稍后再试", null);
					}
					
//					resultMap.put("sendMail", sendMail);
//					resultMap.put("isRegistered", isRegistered);
					
					
					String redisRegisterEmail = Config.REDIS_USER_REGISTER_EMAIL + email;
					
					Map<String, String> map = new HashMap<String,String>();
					map.put("email", email);
					map.put("authCode", authCode);
					jedisPool.getJedis().set(redisRegisterEmail, JacksonUtil.mapToJson(map));
					jedisPool.getJedis().expire(redisRegisterEmail, 30 * 60);
					
					return retContent(RspCode.SUCCESS, null);
					
				}else{
					return retContent(RspCode.USER_STATUS_NOT_REGISTE, null);
				}
				
				
				
			} catch (Exception e) {
				logger.error("忘记密码发送邮件", e);
				return retContent(RspCode.CODE_SERVER_INTERNAL, null);
			}
			
		}
		
		return "";
	}
	
	/**
	 * 注册用户
	 * @param request
	 * @param response
	 * @return
	 * @throws ValidateException
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String register(
			@RequestBody(required = false) Map<String, Object> parmMap, HttpServletResponse response,
				HttpServletRequest request)
					throws ValidateException {
		
		logger.info("入参  ：request =  " + parmMap);
		
		Map<String, Object> resultMap = bulidResultMap();
		
		String backJson = checkParam(parmMap, new String[]{"email", "authCode", "password"}, 
												  new int[]{64,		64,			64});
		
		if(StringUtils.isEmpty(backJson)){
			try {
				int isRegistered = 1;
				List<Map<String, Object>> list = userService.checkUserisRegister(parmMap);
				if(list.size() != 0){
					resultMap.put("isRegistered", isRegistered);
					return retContent(RspCode.CODE_PARAM, "该用户已注册", resultMap);
				}
				String authCode = (String) parmMap.get("authCode");
				String email = (String) parmMap.get("email");
				Map<String, Object> userMap = new HashMap<String, Object>();
				
				String redisRegisterEmail = Config.REDIS_USER_REGISTER_EMAIL + email;
				
				if(!this.jedisPool.getJedis().exists(redisRegisterEmail)){
					return retContent(RspCode.CODE_PARAM, "验证码已过期", null);
				}
				userMap = JacksonUtil.jsonToMap(this.jedisPool.getJedis().get(redisRegisterEmail));
				if(!authCode.equals(userMap.get("authCode")) || !email.equals(userMap.get("email"))){
					return retContent(RspCode.CODE_PARAM, "请输入正确的验证码和邮箱", null);
				}
				//更新用户基本信息
				int maxUserId = userService.getMaxUserId();
				
				UserAccounts user = new UserAccounts();
				user.setUserId(Integer.valueOf(maxUserId) + 1);
				user.setNikeName(email.split("@")[0]);
				user.setEmail(email);
				user.setAuthCode(authCode);
				user.setCreateTime(TimeUtil.currentTimeMillisStr());
				user.setRole(Config.ZERO);
				user.setStatus(Config.ZERO);
				user.setPayScore(Config.ZERO);
				user.setContrbution(Config.ZERO);
				user.setRewardScore(Config.ZERO);
				
//				UserAccounts userAccountsAdd = new UserAccounts();
				user.setPassword(StringUtil.replaceBlank(DESCoderUtil.deCode((String)parmMap.get("password"))));
				user.setStatus(Config.ONE);
				user.setUpdateTime(TimeUtil.currentTimeMillisStr());
				
//				userService.updateByPrimaryKeySelective(userAccountsAdd);
				
				userService.insertSelective(user);

				return retContent(RspCode.SUCCESS, resultMap);
				
			} catch (Exception e) {
				logger.error("注册校验邮箱唯一性错误", e);
				return retContent(RspCode.CODE_SERVER_INTERNAL, null);
			}
			
		}
		
		return "";
	}
	
	/**
	 * 忘记密码
	 * @param request
	 * @param response
	 * @return
	 * @throws ValidateException
	 */
	@RequestMapping(value = "/update_password", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String updatePassword(
			@RequestBody(required = false) Map<String, Object> parmMap, HttpServletResponse response,
			HttpServletRequest request)
					throws ValidateException {
		
		logger.info("入参  ：request =  " + parmMap);
		
		Map<String, Object> resultMap = bulidResultMap();
		
		String backJson = checkParam(parmMap, new String[]{"email", "authCode", "password"}, 
				new int[]{64,		64,			64});
		
		if(StringUtils.isEmpty(backJson)){
			try {
				String authCode = (String) parmMap.get("authCode");
				String email = (String) parmMap.get("email");
				Map<String, Object> userMap = new HashMap<String, Object>();
				
				String redisRegisterEmail = Config.REDIS_USER_REGISTER_EMAIL + email;
				
				if(!this.jedisPool.getJedis().exists(redisRegisterEmail)){
					return retContent(RspCode.CODE_PARAM, "验证码已过期", null);
				}
				userMap = JacksonUtil.jsonToMap(this.jedisPool.getJedis().get(redisRegisterEmail));
				if(!authCode.equals(userMap.get("authCode")) || !email.equals(userMap.get("email"))){
					return retContent(RspCode.CODE_PARAM, "请输入正确的验证码和邮箱", null);
				}
				userMap.put("email", email);
				UserAccounts userAccounts = userService.selectUser(userMap);
				//更新用户基本信息
				UserAccounts userAccountsAdd = new UserAccounts();
				userAccountsAdd.setId(userAccounts.getId());
				userAccountsAdd.setPassword(DESCoderUtil.deCode((String)parmMap.get("password")));
				userAccountsAdd.setUpdateTime(TimeUtil.currentTimeMillisStr());
				userService.updateByPrimaryKeySelective(userAccountsAdd);

				return retContent(RspCode.SUCCESS, resultMap);
				
			} catch (Exception e) {
				logger.error("注册校验邮箱唯一性错误", e);
				return retContent(RspCode.CODE_SERVER_INTERNAL, null);
			}
			
		}
		
		return "";
	}
	
	/**
	 * 绑定板块
	 * @param request
	 * @param response
	 * @return
	 * @throws ValidateException
	 */
	@RequestMapping(value = "/bind_boards", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String bindBoards(
			@RequestBody(required = false) Map<String, Object> parmMap, HttpServletResponse response,
			HttpServletRequest request)
					throws ValidateException {
		
		logger.info("入参  ：request =  " + parmMap);
		
		Map<String, Object> resultMap = bulidResultMap();
		
		String backJson = checkParam(parmMap, new String[]{"email", "boardId"}, 
				new int[]{64,		64});
		
		if(StringUtils.isEmpty(backJson)){
			try {
				
				List<Map<String, Object>> list = userService.checkUserisRegister(parmMap);
				if(list.size() > 0 && list.size() == 1){
					UserAccounts userInfo = new UserAccounts();
					userInfo.setId(Integer.valueOf(String.valueOf(list.get(0).get("id"))));
					userInfo.setFavSectionId((String)parmMap.get("boardId"));
					userService.updateByPrimaryKeySelective(userInfo);
				
					return retContent(RspCode.SUCCESS, resultMap);
				}else{
					return retContent(RspCode.CODE_PARAM, "邮箱在系统中未找到匹配项", null);
				}
				
			} catch (Exception e) {
				logger.error("注册校验邮箱唯一性错误", e);
				return retContent(RspCode.CODE_SERVER_INTERNAL, null);
			}
			
		}
		
		return "";
	}
	
}
