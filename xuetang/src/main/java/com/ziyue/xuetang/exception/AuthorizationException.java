package com.ziyue.xuetang.exception;

public class AuthorizationException extends BaseException {

	/**
	 * 用户验证
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * default constructor
	 */
	public AuthorizationException() {
		super();
	}

	/**
	 * @param message
	 */
	public AuthorizationException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public AuthorizationException(Throwable cause) {
		super(cause);
	}
	
	

}