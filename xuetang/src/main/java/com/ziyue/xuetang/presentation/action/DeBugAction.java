package com.ziyue.xuetang.presentation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziyue.xuetang.exception.ValidateException;
@Controller
public class DeBugAction {
	@RequestMapping(value = "/test/x-collect-client", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody
String appStuVersion(
			HttpServletRequest request ,HttpServletResponse response) throws ValidateException {
		
		String head=request.getHeader ("x-collect-client");
		
		System.out.print(head);
		
		return head;
	}
	
}
