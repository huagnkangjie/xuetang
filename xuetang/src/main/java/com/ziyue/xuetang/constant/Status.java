package com.ziyue.xuetang.constant;

import java.util.HashMap;
import java.util.Map;

import com.ziyue.xuetang.common.message.Messages;



public final class Status {


	public static final int R_OK = 0;

	
	
	//系统错误提示
	public static final int R_10001 = 10001;// 系统错误
	public static final int R_10002 = 10002;// 服务暂停
	public static final int R_10003 = 10003;// 远程服务错误
	public static final int R_10004 = 10004;// 不支持的MediaType (%s)
	public static final int R_10005 = 10005;// 任务超时
	public static final int R_10006 = 10006;// 非法请求(错误的)
	public static final int R_10007 = 10007;// 不合法的用户(token超时
	public static final int R_10008 = 10008;// 缺失参数

	public static final int R_10009 = 10009; // 参数值非法，需为 (%s)，实际为 (%s)
	public static final int R_10010 = 10010;// 请求长度超过限制
	public static final int R_10011 = 10011; // 接口不存在
	public static final int R_10012 = 10012;// 请求的HTTP
											// METHOD不支持，请检查是否选择了正确的POST/GET方式
	
	public static final int R_10013 = 10013;// 参数内容必须填写
	

	
	
	public static final int R_20001 = 20001;// 用户不存在(错误的用户名及密码

	// public static final int R_20002 = 20002;// Token已经被使用
	
	public static final int R_20002 = 20002;// 查找(%s)数据不存在
	
	public static final int R_20003 = 20003;// 错误的用户名或密码
	public static final int R_20004 = 20004;// 创建的简历大于一份

	public static final int R_20005 = 20005;//不能重复点赞
	
		public static final int R_20008 =20008;//信息不完整
		
	public static final int R_20009 = 20009;//验证码发送失败
	
	public static final int R_20007 = 20007;//验证码错误
	
	public static final int R_20006 = 20006;//手机重复注册
	public static final int R_20010= 20010;//上传头像失败

	public static final int R_20011= 20011;//测试时间超时
	public static final int R_20012= 20012;//简历不完整
	public static final int R_20013= 20013;//邀请码错误
	public static final int R_20014= 20014;//请上传头像
	public static final int R_20015= 20015;//%s数据格式错误
	public static final int R_20016= 20016;//教育背景大于一份
	
	public static final int R_20017= 20017;//用户被禁用
	
	public static final int R_20018= 20018;//
	public static final int R_20019= 20019;//学校已经被注册
	
	public static final int R_30000= 30000;//错误提示
	
	
	
	
	
	public static final String POSITION_NOT_FOUND="position_not_found"; //职位未找到
	public static final String COMPANY_NOT_FOUND="company_not_found";//企业未找到

	public static final String RESUME_NOT_FOUND="resume_not_found";//简历未找到
	
	
	public static Map<String, Object> back() {
		
		Map<String, Object> map = new HashMap<String, Object>();

			map.put(Resource.RESP_CODE_KEY, R_OK + "");
			
			map.put(Resource.RESP_MSG_KEY, Messages.getString(Status.R_OK+""));

			return map;
		
	}
	
	


}
