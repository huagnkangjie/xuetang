package com.ziyue.xuetang.constant;
/**
 * @描述： 返回响应码
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年11月23日
 * @version v1.0.
 * 
 */
public enum RspCode {

	/**系统内部错误码*/
	SUCCESS										(0,    "success"),
	
	CODE_SERVER_INTERNAL						(1000, "[服务器]内部错误"),
	CODE_MSG_FORMAT								(1007, "[服务器]消息格式错误"),
	CODE_PARAM									(1008, "[服务器]请求参数非法"),
	CODE_NO_AUTH								(1009, "[服务器]无此操作权限"),
	CODE_TOKEN_EXPIRE							(1010, "[服务器]token过期"),
	CODE_TOKEN_AUTH_ERROR						(1011, "[服务器]token鉴权失败"),
	CODE_SEND_MAIL_FAIL							(1012, "[服务器]邮件发送失败"),
	
	
	/**用户相关的错误码*/
	USER_STATUS_NOT_REGISTE						(1100, "用户未注册"),
	USER_STATUS_NOT_ACTIVED						(1101, "用户未激活"),
	USER_STATUS_NOT_ALLOW_LOGIN					(1102, "用户已禁止登陆"),
	USER_STATUS_NOT_BIND_BOARD					(1103, "用户未绑定板块"),
	USER_STATUS_NOT_QUERY_REPORT_AUTH			(1104, "用户无查询举报列表权限"),

	
	/**问题类的错误码*/
	QUESTION_ADOP_MYSELF						(1201, "不能采纳自己的回复"),
	
    ;

    private int code;

    private String msg;

    RspCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
