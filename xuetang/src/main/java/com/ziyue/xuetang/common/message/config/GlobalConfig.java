package com.ziyue.xuetang.common.message.config;

 
public class GlobalConfig extends ConfigEngine {
	// 配置文件路径
	public static final String CONFIG_FILE_PATH ="/config/global.config";
	public static final String IS_REALSE="is_realse";
	
	public GlobalConfig( ) {
		super(CONFIG_FILE_PATH);
	 
	}
	
	
	/**
	 * 这种写法最大的美在于，完全使用了Java虚拟机的机制进行同步保证。
	 */
	private static class SingletonHolder {
		public final static GlobalConfig instance = new GlobalConfig();
	}

	/**
	 * 获取系统配置实例
	 */
	public static GlobalConfig getInstance() {
		return SingletonHolder.instance;
	}
	
	public String IsRealse() {
		String value = this.properties.get(IS_REALSE).toString();
		return value;
	}
	public static void main(String[] args) {

		GlobalConfig config = GlobalConfig.getInstance();

		System.out.println(config.IsRealse());

	}

	

}
