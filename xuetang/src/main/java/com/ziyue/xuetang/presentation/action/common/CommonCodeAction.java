package com.ziyue.xuetang.presentation.action.common;

import com.ziyue.xuetang.constant.RspCode;
import com.ziyue.xuetang.exception.ValidateException;
import com.ziyue.xuetang.model.user.UserAccounts;
import com.ziyue.xuetang.presentation.action.BaseAction;
import com.ziyue.xuetang.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @描述： 处理公共的业务，主要是码表类的
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年11月20日
 * @version v1.0.
 * 
 */

@Controller
@RequestMapping("/common")
public class CommonCodeAction extends BaseAction{
	
	@Autowired
	private UserService userService;
	
	private static final Logger logger = Logger.getLogger(CommonCodeAction.class);

	/**
	 * 获取标签列表
	 * @param response
	 * @return
	 * @throws ValidateException
	 */
	@RequestMapping(value = "/boards/list/{userId}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String checkRegister(HttpServletResponse response,
			@PathVariable String userId)
					throws ValidateException {
		Map<String, Object> request = new HashMap<String, Object>();
		request.put("userId", userId);
		logger.info("入参  ：request =  " + request);
		
		Map<String, Object> resultMap = bulidResultMap();
		
		String backJson = checkParam(request, new String[]{"userId"}, new int[]{64});
		
		if(StringUtils.isEmpty(backJson)){
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", userId);
				UserAccounts user = userService.selectUser(map);
				
				List<Map<String, Object>> boradsList = new ArrayList<Map<String, Object>>();
				String boradsId = "";
				if(user != null && !StringUtils.isEmpty(user.getFavSectionId())){
					boradsId = user.getFavSectionId();
					boradsList = userService.getTagsList(boradsId);
				}
				
				logger.info("用户绑定的boradsId = " + boradsId);
				
				resultMap.put("boradsList", boradsList);
				
				return retContent(RspCode.SUCCESS, resultMap);
				
			} catch (Exception e) {
				logger.error("获取用户绑定的标签列表", e);
				return retContent(RspCode.CODE_SERVER_INTERNAL, null);
			}
			
		}
		
		return "";
	}
	
}
