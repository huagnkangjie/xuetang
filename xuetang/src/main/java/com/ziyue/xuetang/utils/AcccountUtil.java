package com.ziyue.xuetang.utils;

import org.springframework.util.StringUtils;

import com.ziyue.xuetang.core.config.impl.HeadFtpConfig;
 
public class AcccountUtil {

	
	
	
	public static String repaceNickName(String nickName){
	nickName=nickName.replace(",", "");
		
	 return nickName;
	}
	
	
	
	
	public static String accountBgUrl(String bgUrl){
		if (!StringUtils.isEmpty(bgUrl)) {

			if (!bgUrl.contains("http://") &&!bgUrl.contains("https://")     ) {
				bgUrl = HeadFtpConfig.getInstance().getFtpBgImgUrl() + bgUrl;
		       
			}

		}
		
		return bgUrl;
		
	}
	
	
	
	public static String accountHeadUrl(String headUrl){
		if (!StringUtils.isEmpty(headUrl)) {

			if (!headUrl.contains("http://") &&!headUrl.contains("https://")     ) {
				headUrl = HeadFtpConfig.getInstance().getFtpImgUrl() + headUrl;
		       
			}

		}
		
		return headUrl;
		
	}
	
	
}
