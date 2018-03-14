package com.ziyue.xuetang.exception;

import com.ziyue.xuetang.constant.RspCode;

public class ValidateException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * default constructor
	 */
	public ValidateException() {
		super();
	}

	/**
	 * @param message
	 */
	public ValidateException(String message) {
		super(message);
	}
	
	
	
	/**
	 * @param message
	 */
	public ValidateException(int code,String message) {
		super(message);
		this.code=code;
	}
	
	/**
	 * 可以自定义返回的信息
	 * @param rspCode
	 * @param message
	 */
	public ValidateException(RspCode rspCode, String message) {
		super(message);
		this.code=rspCode.getCode();
	}
	
	/**
	 * 用默认的错误码和返回信息
	 * @param rspCode
	 */
	public ValidateException(RspCode rspCode) {
		super(rspCode.getMsg());
		this.code=rspCode.getCode();
	}
	
	
	

	/**
	 * @param message
	 * @param cause
	 */
	public ValidateException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public ValidateException(Throwable cause) {
		super(cause);
	}
	
	

}