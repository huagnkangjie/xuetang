package com.ziyue.xuetang.presentation.action.student;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziyue.xuetang.exception.ValidateException;
import com.ziyue.xuetang.presentation.action.BaseAction;

/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年11月6日
 * @version v1.0.
 * 
 */
@Controller
@RequestMapping("/stu/kill")
public class TestAction2 extends BaseAction{

	@RequestMapping(value = "/testyyy2", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody ResponseEntity<Object> testyyy(
			@RequestBody(required = false) Map<String, Object> request, HttpServletResponse response)
					throws ValidateException {
		
		// RequestMap<String, Object> map = DataUtil.convert2Map(request);
		System.out.println("真NB啊");
		
		
		return buildResponseEntity(RandomStringUtils.randomAlphanumeric(4), response);
	}
	
	@RequestMapping(value = "/testyyy3", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody ResponseEntity<Object> testyyy3(
			@RequestBody(required = false) Map<String, Object> request, HttpServletResponse response)
					throws ValidateException {
		
		// RequestMap<String, Object> map = DataUtil.convert2Map(request);
		System.out.println("真NB啊");
		
		String backJson = "{\"sb\":\"whow is sb\"}";
		
		return buildResponseEntity(backJson, response);
	}
}
