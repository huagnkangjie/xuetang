package com.ziyue.xuetang.presentation.action.student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.ziyue.xuetang.common.cache.redis.FailoverJedisPool;
import com.ziyue.xuetang.common.message.Messages;
import com.ziyue.xuetang.constant.Config;
import com.ziyue.xuetang.constant.Constants;
import com.ziyue.xuetang.constant.Status;
import com.ziyue.xuetang.exception.ValidateException;
import com.ziyue.xuetang.model.User;
import com.ziyue.xuetang.presentation.action.BaseAction;
import com.ziyue.xuetang.service.test.TestService;
import com.ziyue.xuetang.utils.FastJsonUtil;
import com.ziyue.xuetang.utils.JacksonUtil;
import com.ziyue.xuetang.utils.mail.MailVo;
import com.ziyue.xuetang.utils.mail.SendMailManager;

/**
 * @鎻忚堪锛�
 *
 * @author 浣滆�� : huang_kangjie
 * @date 鍒涘缓鏃堕棿锛�2017骞�10鏈�29鏃�
 * @version v1.0.
 * 
 */
@Controller

@RequestMapping("/stu/kill")
public class TestAction extends BaseAction{
	
	private Logger logger = Logger.getLogger(TestAction.class);
	
	@Autowired
	private TestService service;
	
	@Autowired
    private FailoverJedisPool jedisPool;
	
	@RequestMapping(value = "/getacount", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody ResponseEntity<Object> getacount(
			@RequestBody(required = false) Map<String, Object> request, HttpServletResponse response)
					throws ValidateException {

		// RequestMap<String, Object> map = DataUtil.convert2Map(request);

		//heckToken(request, Constants.TOKEN_PREFIX_STU);

		Object strJson = "sdf";

		if (strJson == null) {
			throw new ValidateException(Status.R_10001, Messages.getString(Status.R_10001 + "", ""));
		}

		return buildResponseEntity(strJson, response);

	}
	
	@RequestMapping(value = "/testyy", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody ResponseEntity<Object> testyy(
			@RequestBody(required = false) Map<String, Object> request, HttpServletResponse response)
					throws ValidateException {
		
		// RequestMap<String, Object> map = DataUtil.convert2Map(request);
		
		
		Object strJson = service.getAcounts(request);
		
		return buildResponseEntity(strJson, response);
	}
		
		@RequestMapping(value = "/testyyy", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
		public @ResponseBody String testyyy(
				@RequestBody(required = false) Map<String, Object> request, HttpServletResponse response)
						throws ValidateException {
			
			String token = checkToken(request);
			
			// RequestMap<String, Object> map = DataUtil.convert2Map(request);
			
			System.out.println(">>>>>>>>>>>> "+RandomStringUtils.random(5));
			
			logger.info("this a log test");
			System.out.println(service.getAcounts(request));
			
			List<Map<String, Object>> list = service.getAcounts(request);
			
			
			
			Map<String, String> map = new HashMap<String, String>();
            map.put("testsn", "this is a test");
            
			
			String flag = jedisPool.getJedis().hmset("test", map);
			
			System.out.println(">>>>>>>> flag = " + flag);
			
			System.out.println(service.getAcounts(request));
			
			String strJson = FastJsonUtil.objToJson(list);
			try {
//				System.out.println(1/0);
			} catch (Exception e) {
				logger.error("闃挎柉钂傝姮", e);
				throw new ValidateException(3, "除数不能为0");
			}
			
//			MailVo mail = new MailVo();
//			mail.setReceiver("382576884@qq.com");
//			mail.setSubject("asd");
//			mail.setContent("sdf");
//			boolean falg = SendMailManager.sendMail(mail);
//			logger.info(">>>>>>>>>>>>>>>>>>> " + falg);
			
			//testDao插入回滚
//			try {
//				service.insertTest();
//			} catch (Exception e) {
//				e.printStackTrace();
//				
//			}
			
			Map<String,String> tokenMap = new HashMap<>();
			tokenMap.put("token1", token);
			//jedisPool.getJedis().hmset(Config.REDIS_USER_TOKEN, tokenMap);
			
			
			
			
			return JacksonUtil.mapToJson(tokenMap);
			
	}
		
		
}
