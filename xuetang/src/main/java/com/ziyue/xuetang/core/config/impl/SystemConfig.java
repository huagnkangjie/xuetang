package com.ziyue.xuetang.core.config.impl;

import java.io.IOException;

import com.ziyue.xuetang.common.message.config.ConfigEngine;

 
/**
 * 系统配置类（对Properties进行了简单封装） 用于配置系统配置文件，提供读取和保存两种持久化操作
 * 
 * 
 */
public final class SystemConfig extends ConfigEngine {

	// 配置文件路径
	public static final String CONFIG_FILE_PATH = "/config/site/system.config";

	// 全文索引路径
	public static final String LUCENE_PATH = "lucene_path";

	public static final String POSITION_SHARE_URL = "position_share_url";

	/**
	 * 默认构造方法
	 * 
	 * @throws IOException
	 */
	private SystemConfig() {
		super(CONFIG_FILE_PATH);
	}

	/**
	 * 这种写法最大的美在于，完全使用了Java虚拟机的机制进行同步保证。
	 */
	private static class SingletonHolder {
		public final static SystemConfig instance = new SystemConfig();
	}

	/**
	 * 获取系统配置实例
	 */
	public static SystemConfig getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * 配置文件中属性名称配置
	 */
	public interface Names {
		/** 关键字 */
		String KEYWORDS = "keywords";
		/** 描述信息 */
		String DESCRIPTION = "description";
	}

	/**
	 * 
	 * @return
	 */
	public String getLucenePath() {
		String value = this.properties.get(LUCENE_PATH).toString();
		return value;
	}
	
	/**
	 * 通讯录路径
	 * @return
	 */
	public String getContactPath() {
		String value = this.properties.get("contact_path").toString();
		return value;
	}
	
	
	public String getLuceneUrl(){
	    String value = this.properties.get("lucene_url").toString();
	    return value;
	}
	

	public String getPositionShareUrl() {

		try {
			String value = this.properties.get(POSITION_SHARE_URL).toString();

			return value;
			
		} catch (Exception e) {
			
		}
		return "";
	}

	public static void main(String[] args) {

		SystemConfig config = SystemConfig.getInstance();

		System.out.println(config.getLuceneUrl());

	}

}
