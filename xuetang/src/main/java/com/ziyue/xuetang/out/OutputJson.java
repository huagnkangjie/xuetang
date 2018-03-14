package com.ziyue.xuetang.out;
import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年11月22日
 * @version v1.0.
 * 
 */

public class OutputJson implements Serializable{

    /**
     * 返回客户端统一格式，包括状态码，提示信息，以及业务数据
     */
    private static final long serialVersionUID = 1L;
    //状态码
    private int errorCode;
    //必要的提示信息
    private String description;
    //业务数据
    private Object data;

    public OutputJson(){
    	
    }
    
    public OutputJson(int errorCode,String description,Object data){
        this.errorCode = errorCode;
        this.description = description;
        this.data = data;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String toString(){
	    if(null == this.data){
	        this.setData(new Object());
	    }
	    return JSON.toJSONString(this);
	}
}
