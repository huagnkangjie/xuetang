package com.ziyue.xuetang.presentation.action.student;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziyue.xuetang.constant.RspCode;
import com.ziyue.xuetang.exception.ValidateException;
import com.ziyue.xuetang.presentation.action.BaseAction;
import com.ziyue.xuetang.presentation.action.BaseController;

/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年11月22日
 * @version v1.0.
 * 
 */
@Controller
@RequestMapping("/test3")
public class TestAction3 extends BaseAction {

	@RequestMapping(value="/test",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
    public String sendMessage(String username,String forType,String userType) throws ValidateException
	{
		
		if(null == username || "".equals(username)){
			throw new ValidateException(RspCode.CODE_PARAM.getCode(), "asdfasdf");
        }
		try {
			System.out.println(1/0);
		} catch (ValidateException e) {
			throw new ValidateException(RspCode.CODE_PARAM.getCode(), "asdfasdf");
		}
		
		
        if(null == username || "".equals(username)){
            return retContent(RspCode.CODE_PARAM, null);
        }
        if(!"user".equals(userType) && !"merchant".equals(userType)){
            return retContent(2029, null);
        }
        if(!"register".equals(forType) && !"backpwd".equals(forType)){
            return retContent(2029, null);
        }
        
        return "{\"test\":\"ok\"}";
    }
}
