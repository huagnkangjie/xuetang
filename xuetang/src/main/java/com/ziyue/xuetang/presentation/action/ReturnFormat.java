package com.ziyue.xuetang.presentation.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.collect.Maps;
import com.ziyue.xuetang.constant.RspCode;
import com.ziyue.xuetang.out.OutputJson;
import com.ziyue.xuetang.utils.JacksonUtil;

/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年11月22日
 * @version v1.0.
 * 
 */
//格式化返回客户端数据格式（json）
public class ReturnFormat {
	
	private static Logger logger = Logger.getLogger(ReturnFormat.class);
	
    private static Map<String,String>messageMap = Maps.newHashMap();
    //初始化状态码与文字说明
    static {
        messageMap.put("0", "");

        messageMap.put("400", "Bad Request!");
        messageMap.put("401", "NotAuthorization");
        messageMap.put("405", "Method Not Allowed");
        messageMap.put("406", "Not Acceptable");
        messageMap.put("500", "Internal Server Error");

        messageMap.put("1000", "[服务器]内部错误");
        messageMap.put("1001", "[服务器]空值异常");
        messageMap.put("1002", "[服务器]数据类型转换异常");
        messageMap.put("1003", "[服务器]IO异常");
        messageMap.put("1004", "[服务器]未知方法异常");
        messageMap.put("1005", "[服务器]数组越界异常");
        messageMap.put("1006", "[服务器]网络异常");
        
    }
    public static String retParam(int status,Object data) {
        OutputJson json = new OutputJson(status, messageMap.get(String.valueOf(status)), data);
        return json.toString();
    }
    
    public static String retParam(RspCode rspCode, Object data) {
    	OutputJson json = new OutputJson();
    	json.setErrorCode(rspCode.getCode());
    	json.setDescription(rspCode.getMsg());
    	try {
    		String rs = "";
    		if(null == data){
    			String s1 =  JacksonUtil.objToJson(json);
    			rs = s1.substring(0, s1.length() - 1) + ",\"data\":{}}";
    	    }else{
    	    	json.setData(data);
    	    	rs = JacksonUtil.objToJson(json);
    	    }
			return rs;
		} catch (IOException e) {
			logger.error("", e);
			OutputJson jsonErro = new OutputJson(1000, messageMap.get(String.valueOf(1000)), data);
		    return jsonErro.toString();
		}
    }
}
