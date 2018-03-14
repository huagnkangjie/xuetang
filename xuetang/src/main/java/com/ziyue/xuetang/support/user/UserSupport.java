package com.ziyue.xuetang.support.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ziyue.xuetang.constant.Config;
import com.ziyue.xuetang.constant.RspCode;
import com.ziyue.xuetang.exception.ValidateException;
import com.ziyue.xuetang.model.user.UserAccounts;
import com.ziyue.xuetang.service.user.UserService;
import com.ziyue.xuetang.utils.DESCoderUtil;
import com.ziyue.xuetang.utils.FileUtil;
import com.ziyue.xuetang.utils.JacksonUtil;
import com.ziyue.xuetang.utils.StringUtil;

/**
 * @描述：用户基础支撑
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年12月21日
 * @version v1.0.
 * 
 */

@Component
public class UserSupport {

	@Resource
	private UserService userService;
	
	private static Logger logger = Logger.getLogger(UserSupport.class);
	
	/**
	 * 搜藏问题、取消收藏
	 * @param map
	 */
	public void collectionQuestion(Map<String, Object> map) throws ValidateException{
		int flag = StringUtil.getIntValue(map, "flag");
		//收藏
		if(flag == 0){
			userService.collectionQuestion(map);
		}
		
		//取消收藏
		if(flag == 1){
			userService.canselCollectionQuestion(map);
		}
	}
	
	/**
	 * 移除重要字段
	 * @param usreInfo
	 * @return
	 */
	public UserAccounts removeFields(UserAccounts usreInfo){
		usreInfo.setPassword(null);
		usreInfo.setAuthCode(null);
		usreInfo.setCreateTime(null);
		usreInfo.setLastLoginIp(null);
		usreInfo.setLastLoginTime(null);
		usreInfo.setUpdateTime(null);
		return usreInfo;
		
	}
	
	/**
	 * 用户更新、修改
	 * @throws ValidateException
	 */
	public void update(Map<String, Object> map) throws ValidateException{
		
		String json = JacksonUtil.mapToJson(map);
		UserAccounts user = null;
		try {
			user = (UserAccounts) JacksonUtil.jsonToObj(json, UserAccounts.class);
		} catch (Exception e) {
			logger.error("修改基本信息转换json 出错", e);
			e.printStackTrace();
			throw new ValidateException(RspCode.CODE_SERVER_INTERNAL);
		}
		UserAccounts selectUser = userService.selectUserByUserId(StringUtil.getIntValue(map, "userId"));
		
		String favSectionId = StringUtil.getStringValue(map, "boardId");
		user.setFavSectionId(favSectionId);
		user.setId(selectUser.getId());
		user.setNikeName(StringUtil.getStringValue(map, "nickName"));
		userService.updateByPrimaryKeySelective(user);
		
	}
	
	/**
	 * 更新密码
	 */
	public void updatePassword(Map<String, Object> map) throws ValidateException{
		
		String poassword = StringUtil.getStringValue(map, "password");
		String newPwd = StringUtil.getStringValue(map, "newPwd");
		
		UserAccounts selectUser = userService.selectUserByUserId(StringUtil.getIntValue(map, "userId"));
		String s1 = StringUtil.replaceBlank(DESCoderUtil.deCode(poassword));
		String s2 = StringUtil.replaceBlank(selectUser.getPassword());
		logger.debug("新密码：" + s1);
		logger.debug("原密码：" + s2);
		if(!s1.equals(s2)){
			throw new ValidateException(RspCode.CODE_PARAM, "请输出正确的密码");
		}
		
		UserAccounts user = new UserAccounts();
		user.setId(selectUser.getId());
		user.setPassword(DESCoderUtil.deCode(newPwd));
		userService.updateByPrimaryKeySelective(user);
	}
	
	/**
	 * 更新头像
	 */
	public void updateLogo(Map<String, Object> map) throws ValidateException{
		UserAccounts selectUser = userService.selectUserByUserId(StringUtil.getIntValue(map, "userId"));
		String url = StringUtil.getStringValue(map, "url");
		
		String logo = FileUtil.copyFile(Config.FILE_PATH_LOGO, selectUser.getUserId()+"", url);
		
		UserAccounts user = new UserAccounts();
		user.setId(selectUser.getId());
		user.setHeadUrl(logo);
		userService.updateByPrimaryKeySelective(user);
	}
	
	
}
