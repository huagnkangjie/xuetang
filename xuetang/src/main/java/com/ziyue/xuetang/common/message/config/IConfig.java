package  com.ziyue.xuetang.common.message.config;
import java.util.Properties;

 

/**
 * 配置引擎接口
 * 
 
 */
public interface IConfig{
	
	
	/**
	 * 获取值
	 * @param key
	 * @return
	 */
	String get(String key);
	
	/**
	 * 设置键值
	 * @param key
	 * @param value
	 */
	void set(String key, String value);
	
	
	/**
	 * 获取内部Properties
	 * @return
	 */
	Properties getProperties();
	
	
	/**
	 * 持久化配置文件
	 */
	void store();
	
}
