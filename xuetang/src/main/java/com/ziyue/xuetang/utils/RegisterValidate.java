package com.ziyue.xuetang.utils;

public class RegisterValidate {
	/**
	 * 验证输入的邮箱格式是否符合
	 * 
	 * @param email
	 * @return 是否合法
	 */
	public static boolean emailValidate(String email) {
		// 验证邮箱的正则表达式
		String format = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

		if (email.matches(format)) {
			return true;// 邮箱名合法，返回true
		} else {
			return false;// 邮箱名不合法，返回false
		}

	}

	public static boolean phoneValidate(String phone) {

		String format = "^[1][0-9][0-9]{9}$";

		if (phone.matches(format)) {
			return true;// 合法，返回true
		} else {
			return false;// 不合法，返回false
		}

	}

	public static void main(String args[]) {

	 System.out.println(emailValidate("aa@126.com"));
		System.out.println(phoneValidate("13211111112"));

	}

}
