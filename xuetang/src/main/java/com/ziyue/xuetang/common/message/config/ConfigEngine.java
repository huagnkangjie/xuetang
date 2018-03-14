package  com.ziyue.xuetang.common.message.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 抽象配置引擎类
 * 
 
 * */
public abstract class ConfigEngine implements IConfig {

	/** 日志记录器 */ 
	protected Logger logger =  LoggerFactory.getLogger(ConfigEngine.class);
	
	
	/** 
	 * 配置信息存放对象
	 *(API: Properties类是线程安全的：多个线程可以共享单个Properties对象而无需进行外部同步)
	 * */
	protected Properties properties = new Properties();
	
	/** 配置文件编码集UTF-8 */
	public static final String FILE_ENCODEING = "utf-8";
	
	/** 系统版权信息 */
	public static final String COPY_RIGHT = "Power By  ziyuexuetang";
	
	/** 配置文件路径 */
	protected File cfgFile;
 
	
	/**
	 * 初始化就读取配置文件哦
	 * @param string
	 */
	public ConfigEngine(String cfgFilePath) { 
		String file = null;
		try {
			file = ConfigEngine.class.getResource(cfgFilePath).toURI().getPath();
		} catch (URISyntaxException e) { 
			e.printStackTrace();
		} 
		this.read(new File(file));
	}


	/**
	 * 获取配置字符串
	 * @param key 键
	 * @return String 
	 */
	@Override
	public String get(String key) {
		return this.properties.getProperty(key);
	}

	
	/**
	 * 设置配置key = val
	 * @param key 键
	 * @param val 值
	 */
	@Override
	public void set(String key, String val) {
		this.properties.put(key, val);
	}

	
	
	@Override
	public Properties getProperties() {
		return this.properties;
	}

	


	/**
	 * 读取配置文件
	 * @param 配置文件File对象
	 */
	public void read(File _cfgFile){
		this.cfgFile = _cfgFile;
		FileInputStream in = null;
		InputStreamReader isr = null;
		try {
			in = new FileInputStream(_cfgFile); 
			isr = new InputStreamReader(in, FILE_ENCODEING);
			this.properties.load(isr);//读取配置文件 
		}catch (FileNotFoundException e) {
			logger.error("config file not found " + cfgFile.getAbsolutePath(), e);
		} catch (UnsupportedEncodingException e) {
			logger.error(cfgFile.getAbsolutePath()+" not supported encoding " + FILE_ENCODEING,e);
		} catch (IOException e) {
			logger.error("IOException " + cfgFile.getAbsolutePath(), e);
		}finally{
			try {
				if (isr != null) {
					isr.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				logger.error(
						"close stream IOException "
								+ cfgFile.getAbsolutePath(), e);
			}
		}
	}
	
	
	
	/**
	 * 配置持久化
	 */
	@Override
	public void store(){ 
		OutputStream out = null;
		OutputStreamWriter osw = null;
		try{
			out = new FileOutputStream(cfgFile);
			osw = new OutputStreamWriter(out, FILE_ENCODEING);
			this.properties.store(osw, COPY_RIGHT);
			
		}catch (FileNotFoundException e) {
			logger.error("config file not found " + cfgFile.getAbsolutePath(), e);
		} catch (UnsupportedEncodingException e) {
			logger.error("not supported encoding " + FILE_ENCODEING,e);
		} catch (IOException e) {
			logger.error("IOException " + cfgFile.getAbsolutePath(), e);
		}finally{
			try {
				if(osw != null){
					osw.close(); 
				}
				if(out != null){
					out.close();
				}
			} catch (IOException e) {
				logger.error(
						"close stream IOException "
								+ cfgFile.getAbsolutePath(), e);
			}
		}
	}
 
}



