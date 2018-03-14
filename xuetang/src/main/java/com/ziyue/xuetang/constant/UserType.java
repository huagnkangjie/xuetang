package com.ziyue.xuetang.constant;

/**
 * 用户类型
 * @author Tony
 *
 */
public   enum UserType {
	// 学生
	student(10),
	// 企业
	company(11),
	// 学校
	university(12);

	private int value;

	private UserType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
