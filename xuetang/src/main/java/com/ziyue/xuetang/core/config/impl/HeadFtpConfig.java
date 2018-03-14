package com.ziyue.xuetang.core.config.impl;

import com.ziyue.xuetang.common.message.config.ConfigEngine;

public class HeadFtpConfig extends ConfigEngine {

	// 配置文件路径
	public static final String CONFIG_FILE_PATH = "/config/ftp/ftp.config";

	// 主机
	public static final String HEAD_FTP_HOST = "head_ftp_host";

	// 用户
	public static final String HEAD_USER = "head_user";

	// 密码
	public static final String HEAD_PASSWORD = "head_password";

	public static final String HEAD_PATH = "head_path";
	public static final String BG_PATH = "bg_path";
	public static final String RESUME_PATH = "resume_path";

	public static final String HEAD_PORT = "head_port";

	public static final String HEAD_URL = "head_url";
	
	public static final String BG_URL = "bg_url";
	
	

	public HeadFtpConfig() {
		super(CONFIG_FILE_PATH);

	}

	/**
	 * 这种写法最大的美在于，完全使用了Java虚拟机的机制进行同步保证。
	 */
	private static class SingletonHolder {
		public final static HeadFtpConfig instance = new HeadFtpConfig();
	}

	/**
	 * 获取系统配置实例
	 */
	public static HeadFtpConfig getInstance() {
		return SingletonHolder.instance;
	}

	
	public String getFtpImgUrl(){
		
		String url=getHeadUrl()+"/"+getHeadPath()+"/";
		
		return url;
	}
	
	
	public String getFtpBgImgUrl(){
		
		String url=getBgUrl()+"/"+getBgPath()+"/";
		
		return url;
	}
	
	
	
 
	/**
	 * 主机
	 * 
	 * @return
	 */
	public String getHeadFtpHost() {
		return this.properties.getProperty(HEAD_FTP_HOST);
	}

	public String getHeadUser() {
		return this.properties.getProperty(HEAD_USER);
	}

	public String getHeadPassword() {
		return this.properties.getProperty(HEAD_PASSWORD);
	}

	public String getHeadPath() {
		return this.properties.getProperty(HEAD_PATH);
	}

	public String getResumePath() {
		return this.properties.getProperty(RESUME_PATH);
	}

	public String getBgPath() {
		return this.properties.getProperty(BG_PATH);
	}

	
	public String getHeadUrl() {
		return this.properties.getProperty(HEAD_URL);
	}
	
	public String getBgUrl() {
		return this.properties.getProperty(BG_URL);
	}

	public String getHeadPort() {
		return this.properties.getProperty(HEAD_PORT);
	

	}
	
	
	
	
	
	
	

	public static void main(String[] args) {

		HeadFtpConfig config = HeadFtpConfig.getInstance();

		System.out.println(config.getHeadFtpHost());

	}

}
