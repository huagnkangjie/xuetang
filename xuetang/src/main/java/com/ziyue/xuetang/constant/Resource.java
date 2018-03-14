package com.ziyue.xuetang.constant;

public interface Resource {

	/**
	 * jsonp方法参数名
	 */
	public static final String RESP_CALLBACK = "callback";

	public static final String REQ_METHOD_GET = "GET";

	public static final String REQ_METHOD_POST = "POST";

	public static final String REQ_METHOD_POST_LIST = "POST_LIST";

	public static final String REQ_METHOD_DELETE = "DELETE";

	public static final String REQ_METHOD_PUT = "PUT";

	public static final String REQ_ACCESS_TOKEN = "access_token";

	public static final String REST_SYSTEM_ADMIN_RESOURCES_COUNT_RESULT_KEY = "count";

	public static final String TABLE_OPTION_READ = "read";
	public static final String TABLE_OPTION_INSERT = "insert";
	public static final String TABLE_OPTION_UPDATE = "update";
	public static final String TABLE_OPTION_REMOVE = "remove";

	public static final String SCRIPT_VALUE_NULL = "null";
	
	
	/**平台用户区分*/

	public static final String S_WEI_XIN_KEY = "weixin";

	public static final String S_PASSWORD_KEY = "password";
	
	
	
	

	public static final String SIMPLE_ERROR_CODE_HEADER = "X-Error-Code";
	
	
	/**
	 * 返回码
	 */
	public static final String RESP_CODE_KEY = "errorCode";
	/**
	 * 返回错误
	 */
	public static final String RESP_MSG_KEY = "description";
	

	/**
	 * 使用此请求头提供用户名密码。
	 */
	public static final String SIMPLE_AUTH_HEADER = "X-Auth";

	/**
	 * 使用此请求头提供请求的时间戳。格式为毫秒数。
	 */
	public static final String SIMPLE_AUTH_TIMESTAMP_HEADER = "X-Auth-Timestamp";

}
