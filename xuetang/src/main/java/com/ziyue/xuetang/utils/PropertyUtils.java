/*
 * 文 件 名:  PropertyUtil.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014年4月11日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.ziyue.xuetang.utils;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件/config/config.properties的工具类
 * 
 * 此工具类会动态读取配置文件，配置文件修改后会重新加载配置项。
 * @author  Administrator
 * @version  [版本号, 2014年4月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PropertyUtils
{
    private static Properties props = new Properties();
    
    private static Logger logger = Logger.getLogger(PropertyUtils.class);
    //配置文件
    private static File configFile;
    
    //配置文件的最后修改时间
    private static long fileLastModify = 0L;
    
    static
    {
        try
        {
        	String configPath = System.getProperty("config.path");
        	
        	logger.info("初始化配置文件：configPath = " + configPath);
        	if(configPath==null)
        	{
        		logger.info("初始化配置文件：无法找到系统属性 config.path");
        		configPath = PropertyUtils.class.getResource("/config/config.properties")
                        .getPath();
        		configPath = java.net.URLDecoder.decode(configPath,"utf-8");
        		logger.info("初始化配置文件：configPath =" + configPath);
        		configFile = new File(configPath);
        		
        		
        		
        	}
        	else
        	{
        		configFile = new File(configPath);
        	}
            
            load();
        }
        catch (Exception e)
    	{
        	logger.error("初始化配置出错", e);
    		e.printStackTrace();
    	}
    	catch (ExceptionInInitializerError e)
		{
    		
    		logger.error("初始化配置出错", e);
			e.printStackTrace();
		}
        
    }
    
    /**
     * 根据配置文件中的key,查找值
     * 
     * @param name
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getValue(String name)
    {
        long lm = configFile.lastModified();
        if (lm != fileLastModify)
        {
            load();
        }
        return props.getProperty(name);
    }
    
    /**
     * 根据,返回多组数据
     * <功能详细描述>
     * @param name
     * @return [参数说明]
     * 
     * @return String[] [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String[] getValue4Array(String name)
    {
        long lm = configFile.lastModified();
        if (lm != fileLastModify)
        {
            load();
        }
        String perperties = props.getProperty(name);
        String[] valueArray = null;
        if (!StringUtils.isEmpty(perperties))
        {
            valueArray = perperties.split(",");
        }
        return valueArray;
    }
    
    /**
     * 重新加载配置文件配置项
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private static void load()
    {
        try
        {
            InputStream is = new FileInputStream(configFile);
            props.load(is);
            fileLastModify = configFile.lastModified();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
        /*System.out.println(getValue4Array("serverIP")[0]);
        
        String[] ipArray = PropertyUtil.getValue4Array("serverIP");
        List<String> listArray = Arrays.asList(ipArray);
        System.out.println(listArray.contains("192.168.24.23"));
        */
        System.out.println(">>>>>>>>>>>" + PropertyUtils.getValue("user.token.expire"));

    }
    
}
