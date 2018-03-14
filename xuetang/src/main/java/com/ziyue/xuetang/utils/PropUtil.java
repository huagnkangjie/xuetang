package com.ziyue.xuetang.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;


public class PropUtil {

	/** 资源文件名称 */
//	private static final String PROP_FILE = "ftp.properties";
	private static final String PROP_FILE = "config/config.properties";

	/** 资源文件路径 */
	private static final String PROP_FILE_PATH = "";

	/** 文件名 */
	private String filename;

	/** 资源文件 */
	private Properties prop;

	/** 输入流 */
	private InputStream input;

	/** 输出流 */
	private FileOutputStream output;
	
	 //配置文件的最后修改时间
    private static long fileLastModify = 0L;

	/**
	 * 初始化读取properties文件
	 * 
	 */
	public PropUtil() {
		try {
			input = this.getClass().getClassLoader().getResourceAsStream(
					PROP_FILE_PATH + PROP_FILE);
			prop = new Properties();
			prop.load(input);
			input.close();
		} catch (FileNotFoundException e) {
			System.err.println("配置文件" + PROP_FILE + "找不到！");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("读取配置文件" + PROP_FILE + "错误！");
			e.printStackTrace();
		}
	}

	/**
	 * 初始化读取properties文件
	 * 
	 * @param propFile
	 *            properties文件名称
	 */
	public PropUtil(String propFile) {
		try {
			input = this.getClass().getClassLoader().getResourceAsStream(
					propFile);
			BufferedReader bf = new BufferedReader(new InputStreamReader(input, "utf-8"));
			prop = new Properties();
			prop.load(bf);
			input.close();
		} catch (FileNotFoundException e) {
			System.err.println("配置文件" + propFile + "找不到！");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("读取配置文件" + propFile + "错误！");
			e.printStackTrace();
		}
	}

	/**
	 * 得到配置文件
	 * 
	 * @param hs
	 * @return
	 */
	public static String getConfigFile(HttpServlet hs) {
		return getConfigFile(hs, PROP_FILE_PATH + PROP_FILE);
	}

	/**
	 * 得到配置文件
	 * 
	 * @param hs
	 * @param configFileName
	 * @return
	 */
	private static String getConfigFile(HttpServlet hs, String configFileName) {
		String configFile = "";
		ServletContext sc = hs.getServletContext();
		configFile = sc.getRealPath("/" + configFileName);
		if (configFile == null || configFile.equals("")) {
			configFile = "/" + configFileName;
		}
		return configFile;
	}

	/**
	 * 根据key得到值
	 * 
	 * @param itemName
	 * @return
	 */
	public String getValue(String itemName) {
		return prop.getProperty(itemName);
	}

	/**
	 * 根据key得到值
	 * 
	 * @param itemName
	 * @param defaultValue
	 * @return
	 */
	public String getValue(String itemName, String defaultValue) {
		return prop.getProperty(itemName, defaultValue);
	}

	/**
	 * 根据key设置值
	 * 
	 * @param itemName
	 * @param value
	 */
	public void setValue(String itemName, String value) {
		prop.setProperty(itemName, value);
	}

	/**
	 * 保存文件
	 * 
	 * @param filename
	 * @param description
	 * @throws Exception
	 */
	public void saveFile(String filename, String description) throws Exception {
		try {
			File f = new File(filename);
			output = new FileOutputStream(f);
			prop.store(output, description);
			output.close();
		} catch (IOException ex) {
			throw new Exception("无法保存指定的配置文件:" + filename);
		}
	}

	/**
	 * 保存文件
	 * 
	 * @param filename
	 * @throws Exception
	 */
	public void saveFile(String filename) throws Exception {
		saveFile(filename, "");
	}

	/**
	 * 保存文件
	 * 
	 * @throws Exception
	 */
	public void saveFile() throws Exception {
		if (filename.length() == 0)
			throw new Exception("需指定保存的配置文件名");
		saveFile(filename);
	}

	/**
	 * 删除值
	 * 
	 * @param value
	 */
	public void deleteValue(String value) {
		prop.remove(value);
	}
	/**
	 * 根据KEY读取相应的VALUE,来自src
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		String result = null;
		ResourceBundle rb = ResourceBundle.getBundle("src");
		result = rb.getString(key);
		return result;
	}
	public static void main(String args[]) {
		PropUtil pu = new PropUtil("config/config.properties");
		System.out.println(pu.getValue("mail.smtp.username"));
	}
}
