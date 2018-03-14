package com.ziyue.xuetang.presentation.action;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziyue.xuetang.exception.ValidateException;
import com.ziyue.xuetang.presentation.action.question.QuestionAction;
import com.ziyue.xuetang.service.user.UserService;

/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年11月25日
 * @version v1.0.
 * 
 */
@Controller
@RequestMapping("/demo")
public class Demo extends BaseAction {
	
	@Autowired
	private UserService userService;
	
	private static Logger logger = Logger.getLogger(QuestionAction.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String login(
			@RequestBody(required = false) Map<String, Object> paramMap, HttpServletResponse response)
					throws ValidateException {
		
		logger.info("入参  ：request =  " + paramMap);
		
		checkToken(paramMap);
		
		checkParam(paramMap, new String[]{"questionId"}, new int[]{10});
		
		return null;
	}
	
}
