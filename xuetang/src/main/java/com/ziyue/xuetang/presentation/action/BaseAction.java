package com.ziyue.xuetang.presentation.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.ziyue.xuetang.common.RequestMap;
import com.ziyue.xuetang.common.cache.redis.FailoverJedisPool;
import com.ziyue.xuetang.common.message.Messages;
import com.ziyue.xuetang.constant.Config;
import com.ziyue.xuetang.constant.Resource;
import com.ziyue.xuetang.constant.RspCode;
import com.ziyue.xuetang.constant.Status;
import com.ziyue.xuetang.exception.ValidateException;
import com.ziyue.xuetang.model.user.UserAccounts;
import com.ziyue.xuetang.utils.DataUtil;
import com.ziyue.xuetang.utils.JacksonUtil;
import com.ziyue.xuetang.utils.MD5Util;
import com.ziyue.xuetang.utils.PropertyUtils;

/**
 * 基础类，主要用户做一些校验和提供辅助支撑
 * @author huangkangjie
 *
 */
@ControllerAdvice
public class BaseAction {
	
	@Autowired
    public FailoverJedisPool jedisPool;

	private static final Logger LOG = Logger.getLogger(BaseAction.class);

	private ResponseEntity<Object> buildResponseEntity(String error, int code) {

		return new ResponseEntity<Object>(buildErrorMsg(error, code), HttpStatus.OK);

	}

	@SuppressWarnings("unused")
	private ResponseEntity<Object> buildResponseEntity(String error, HttpStatus httpStatus) {

		return new ResponseEntity<Object>(buildErrorMsg(error, httpStatus.value()), httpStatus);

	}
	
	/**
	 * 公共的校验测试方法
	 * @param map 			获取客户端的参数和值
	 * @param conditions	需要校验必填的参数
	 * @return
	 */
	public String checkParam(Map<String, Object> map, String conditions[], int paramLent[]) throws ValidateException{
		
		String backInfo = "";
		
		if(map == null && conditions.length == 0){
			return "";
		}else if(map == null && conditions.length != 0){
			throw new ValidateException(RspCode.CODE_PARAM.getCode(), "参数不能为空");
		}
		
		if(conditions.length != paramLent.length){
			LOG.error("传入参数的长度和参数length长度不同");
			throw new ValidateException(RspCode.CODE_SERVER_INTERNAL.getCode(), "服务器内部错误");
		}
		
		int i = 0;
		for (String str : conditions) {
			
			if(StringUtils.isEmpty(map.get(str))){
				throw new ValidateException(RspCode.CODE_PARAM.getCode(), str +"不能为空！");
			}
			String val = String.valueOf(map.get(str));
			int len = val.length();
			if(len > paramLent[i]){
				throw new ValidateException(RspCode.CODE_PARAM.getCode(), str +"长度超过" + paramLent[i]);
			}
			i++;
		}
		
		return backInfo;
	}
	
	public static String getErrorMsg(int code, String msg){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Config.KEY_ERROR, code);
		map.put(Config.KEY_DISCRIPTION, msg);
		return JacksonUtil.mapToJson(map);
	}
	
	public Map<String, Object> bulidResultMap(){
		Map<String, Object> resultMap = new HashMap<String, Object>();
//		resultMap.put(Config.KEY_ERROR, Config.CODE_SERVER_INTERNAL);
//		resultMap.put(Config.KEY_DISCRIPTION, Config.VAL_DISCRIPTION);
		return resultMap;
	}
	public Map<String, Object> bulidMap(){
		return bulidResultMap();
	}
	
	public Map<String, Object> bulidSuccesResultMap(Map<String, Object> map){
//		map.put(Config.KEY_ERROR, Config.CODE_SUCCESS);
//		map.put(Config.KEY_DISCRIPTION, Config.VAL_SUCCESS);
		return map;
	} 


	/**
	 * 错误的参数
	 * 
	 * @param response
	 * @param e
	 * @return
	 */

//	@ExceptionHandler({ ValidateException.class })
//	protected ResponseEntity<Object> handleValidateException(HttpServletResponse response, ValidateException e) {
//
//		LOG.error("validate error", e);
//
//		int code = e.getCode();
//
//		String error = e.getMessage();
//
//		addXErrorCodeHeader(response, code);
//
//		return buildResponseEntity(error, code);
//
//	}
	
	protected String retContent(int status,Object data) {
        return ReturnFormat.retParam(status, data);
    }
	
	protected String retContent(RspCode rspCode, Object data) {
        return ReturnFormat.retParam(rspCode, data);
    }
	
	protected String retContent(RspCode rspCode, String tip, Object data) {
		rspCode.setMsg(tip);
		return ReturnFormat.retParam(rspCode, data);
	}
	
	public Map<String, Object> getUserInfoCache(Map<String, Object> map){
		
		String token = (String) map.get("token");
		String json = jedisPool.getJedis().get(Config.REDIS_USER_TOKEN + token);
		
		Map<String, Object> rs = JacksonUtil.jsonToMap(json);
		
		return rs;
	}


	protected ResponseEntity<Object> buildResponseEntity(Object obj, HttpServletResponse response) {

		addXErrorCodeHeader(response, Status.R_OK);

		return new ResponseEntity<Object>(obj, HttpStatus.OK);
	}

	/**
	 * get project base path
	 * 
	 * @param request
	 * @return
	 */
	protected String getBasePath(HttpServletRequest request) {
		// String path = request.getContextPath();
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
	}

	public void addXErrorCodeHeader(HttpServletResponse response, Object code) {

		response.setHeader(Resource.SIMPLE_ERROR_CODE_HEADER, code + "");

	}

	public Map<String, Object> buildSuccess(Object code) {

		Map<String, Object> msg = new HashMap<String, Object>();

		msg.put(Resource.RESP_CODE_KEY, code + "");

		return msg;

	}

	protected void checkMap(Map<String, Object> request) {
		if (request == null || request.size() == 0) {

			throw new ValidateException(Status.R_10008, Messages.getString(Status.R_10008 + "", ""));

		}

	}

	/**
	 * 检查toke有效性
	 * @param map 入参的map
	 * @return
	 * @throws ValidateException
	 */
	protected String checkToken(Map<String, Object> map) throws ValidateException{
		//token不能为空
		if(!map.containsKey("token")){
			throw new ValidateException(RspCode.CODE_PARAM, "token不能为空");
		}
		if(!map.containsKey("userId")){
			throw new ValidateException(RspCode.CODE_PARAM, "userId不能为空");
		}
		
		String token = (String) map.get("token");
		String tokenRdisKey = Config.REDIS_USER_TOKEN + token;
		
		boolean flag = jedisPool.getJedis().exists(tokenRdisKey);
		
		if(!flag){
			throw new ValidateException(RspCode.CODE_TOKEN_EXPIRE, "token已过期");
		}
		
		Map<String, Object> cacheMap = JacksonUtil.jsonToMap(jedisPool.getJedis().get(tokenRdisKey));
		
		if(!cacheMap.get("usreId").equals(String.valueOf(map.get("userId")))){
			throw new ValidateException(RspCode.CODE_TOKEN_AUTH_ERROR);
		}
		
		//检查时间
		
		long timestamp = Long.valueOf(String.valueOf(cacheMap.get("timestamp")));
		long now = System.currentTimeMillis() / 1000;
		long time = now - timestamp;
		long outTime = Long.valueOf(PropertyUtils.getValue("user.token.expire"));
		
		/**
		 * 当token在29分或者30分之间的时候，用户在操作，那么把token 更新一下
		 * 主要是防止token一直的重复利用
		 */
		if(time > ((outTime -1) * 60) && time < (outTime * 60)){
			token = createToken();
			cacheMap.put("timestamp", System.currentTimeMillis() / 1000);
			saveToken(token, cacheMap);
		}
		
		return token;
		
	}
	
	/**
	 * 生成随机token
	 * @return
	 * @throws Exception
	 */
	public String createToken(){
		
		try {
			return MD5Util.encrypt(RandomStringUtils.randomAlphanumeric(10));
		} catch (Exception e) {
			return RandomStringUtils.randomAlphanumeric(10);
		}
	}
	
	/**
	 * 保存token
	 * @param token
	 * @param resultMap
	 * @return
	 */
	public boolean saveToken(String token, Map<String, Object> resultMap){
		
		boolean flagSave = false;
		
		resultMap.remove(Config.KEY_DISCRIPTION);
		resultMap.remove(Config.KEY_ERROR);
		
		token = Config.REDIS_USER_TOKEN + token;
		
		String flag = "";
		try {
			
			flag = jedisPool.getJedis().set(token, JacksonUtil.mapToJson(resultMap));
			int time = Integer.valueOf(PropertyUtils.getValue(Config.REDIS_USER_TOKEN_EXPIRE));
			jedisPool.getJedis().expire(token, time * 60);
			
		} catch (Exception e) {
			flag = "error";
			LOG.error("用户数据保存redis出错", e);
		}
		
		if("OK".equals(flag)){
			flagSave = true;
		}
		
		return flagSave;
	}
	
	/**
	 * 获取用户id
	 * @param paramMap
	 * @return
	 */
	public String getUserId(Map<String, Object> paramMap){
		return (String) paramMap.get("userId");
	}
	
	public static void main(String[] args) {
		long timestamp = Long.valueOf(String.valueOf("1511597327"));
		long now = System.currentTimeMillis() / 1000;
		long time = now - timestamp;
		long outTime = Long.valueOf(PropertyUtils.getValue("user.token.expire"));
		try {
			System.out.println("时间戳	：  "+timestamp);
			System.out.println("当前时间："+now);
			System.out.println("差：            "+time);
			System.out.println("过期时间："+outTime);
		} catch (Exception e) {
			// TODO: handle exception
		}
		long now1 = System.currentTimeMillis();
		
		System.out.println(now1 - now);
	}


	public Map<String, Object> buildErrorMsg(Exception ex, Object code) {
		Map<String, Object> msg = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(ex.getMessage())) {

			msg.put(Resource.RESP_MSG_KEY, ex.getMessage());
			msg.put(Resource.RESP_CODE_KEY, code + "");

		} else {
			msg.put(Resource.RESP_MSG_KEY, "出现内部错误");
			msg.put(Resource.RESP_CODE_KEY, code + "");
		}
		return msg;
	}

	private Map<String, Object> buildErrorMsg(String error, Object code) {
		Map<String, Object> msg = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(error)) {

			msg.put(Resource.RESP_MSG_KEY, error);
			msg.put(Resource.RESP_CODE_KEY, code + "");

		} else {
			msg.put(Resource.RESP_MSG_KEY, "出现内部错误");
			msg.put(Resource.RESP_CODE_KEY, code + "");
		}
		return msg;
	}

	public String getAttribute(Map<String, Object> map, String name) {
		if (null != map && map.containsKey(name)) {
			return String.valueOf(map.get(name));
		}
		return null;
	}

	public void checkAttributeKey(Map<String, Object> map, String... parms) {

		if (map == null || map.size() == 0) {

			throw new ValidateException(Status.R_10008, Messages.getString(Status.R_10008 + "", ""));

		}
		if (parms == null || parms.length == 0) {

			throw new ValidateException(Status.R_10008, Messages.getString(Status.R_10008 + "", ""));
		}

		String key;
		for (int pos = 0, len = parms.length; pos < len; pos++) {
			key = parms[pos];

			if (!map.containsKey(key)) {

				throw new ValidateException(Status.R_10008, Messages.getString(Status.R_10008 + "", key));

			}

		}

	}

	public void checkAttribute(Map<String, Object> map, String... parms) {

		if (map == null || map.size() == 0) {

			throw new ValidateException(Status.R_10008, Messages.getString(Status.R_10008 + "", ""));

		}

		if (parms == null || parms.length == 0) {

			throw new ValidateException(Status.R_10008, Messages.getString(Status.R_10008 + "", ""));
		}

		String key;

		Object value;

		for (int pos = 0, len = parms.length; pos < len; pos++) {
			key = parms[pos];

			if (!map.containsKey(key)) {

				throw new ValidateException(Status.R_10008, Messages.getString(Status.R_10008 + "", key));

			}

			value = MapUtils.getObject(map, key);

			if (value instanceof List) {

				List val = (List) value;
				if (val == null || val.size() == 0) {
					throw new ValidateException(Status.R_10013, Messages.getString(Status.R_10013 + "", key));

				}

			}	else	   {
				String val = String.valueOf(value);
				
				
				
				if (StringUtils.isEmpty(val) || val.equals("null")) {
					throw new ValidateException(Status.R_10013, Messages.getString(Status.R_10013 + "", key));

				}

			}  

		}

	}
	
	/**
	 * 获取get请求中的所有参数
	 * @param request
	 * @return
	 */
	public Map<String, Object> getParamsByGet(HttpServletRequest request) throws ValidateException{
		Map<String, Object> map = new  HashMap<String, Object>();
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String param = (String) params.nextElement();
			map.put(param, request.getParameter(param));
		}
		return map;
	}
	

}
