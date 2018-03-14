package com.ziyue.xuetang.constant;

import java.text.MessageFormat;
import java.util.Random;

import com.ziyue.xuetang.utils.PropUtil;

/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年11月3日
 * @version v1.0.
 * 
 */
public class Config {

	//用户在redis的token存放的key
	public static final String REDIS_USER_TOKEN = "user.token.";
	
	//用户token过期时间30分钟
	public static final String REDIS_USER_TOKEN_EXPIRE = "user.token.expire";
	
	//用户注册时候的emial
	public static final String REDIS_USER_REGISTER_EMAIL = "user.register.emial.";
	
	
	//邮件配置
	public static String CONFIG_PATH = "config/config.properties";
	public static String MAIL_SMTP_HOST = "mail.smtp.host";
	public static String MAIL_SMTP_PORT = "mail.smtp.port";
	public static String MAIL_SMTP_USERNAME = "mail.smtp.username";
	public static String MAIL_SENDER = "mail.sender";
	public static String MAIL_SMTP_PASSWORD = "mail.smtp.password";
	
	public static String KEY_ERROR = "errorCode";
	public static String KEY_DISCRIPTION = "description";
	
	public static String VAL_SUCCESS = "success";
	public static String VAL_DISCRIPTION = "[服务器]内部错误";
	
	public static String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "abcdefghjkmnpqrstuvwxyz23456789ABCDEFGHJKLMNPQRSTUVWXYZ";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 }   

	public static void main(String[] args) {
		System.out.println(getRandomString(6));
		
		String template = new PropUtil().getValue("regesiter.template");
		template = MessageFormat.format(template, getRandomString(6));
		
		System.out.println(template);
		
		System.out.println(MessageFormat.format("我是{0},我来自{1},今年{2}岁", "中国人", "北京", "22"));
	}
	
	
	public static String ZERO = 	"0";
	public static String ONE = 		"1";
	public static String TWO = 		"2";
	public static String THREE = 	"3";
	public static String FOUR = 	"4";
	public static String FIVE = 	"5";
	
	

	public static String FILE_PATH_QUESTION = "question";	//问题图片存放目录
	public static String FILE_PATH_LOGO = "logo";			//头像图片存放目录
	
	
	public static String QUESTION_INDEX_LIST = 				"0";		//问题首页列表
	public static String QUESTION_CREATE_LIST = 			"1";		//自己创建的列表
	public static String QUESTION_ANSWER_LIST = 			"2";		//参与回答的列表
	public static String QUESTION_COLLECTION_LIST = 		"3";		//收藏的问题列表
	public static String QUESTION_REPORT_LIST = 			"4";		//举报的问题列表

}
