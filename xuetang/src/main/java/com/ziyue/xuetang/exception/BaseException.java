package com.ziyue.xuetang.exception;

import org.apache.commons.lang.StringUtils;

public class BaseException extends RuntimeException {

   
    private static final long serialVersionUID = 1L;


    
    protected int  code;
    

    /**
     * default constructor
     */
    public BaseException() {
        super();
    }

    /**
     * @param message
     */
    public BaseException(String message) {
        super(message);
        
    }

    
    
    
    
    /**
     * @param message
     * @param cause
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
       
    }

    /**
     * @param cause
     */
    public BaseException(Throwable cause) {
        super(cause);
    }


    
    public int getCode(){
 		return this.code;
 	}
 	

    static String resolveMessage(String message) {
        if (StringUtils.isNotEmpty(message)) {
            if (message.indexOf("-") != -1) {
                String[] msgs = message.split("-");
                return msgs[1];
            } else {
                return message;
            }
        }
        return null;
    }
    
 

    static String resolveNumber(String message) {
        if (StringUtils.isNotEmpty(message)) {
            if (message.indexOf("-") != -1) {
                String[] msgs = message.split("-");
                return msgs[0];
            } else {
                return null;
            }
        }
        return null;
    }

}