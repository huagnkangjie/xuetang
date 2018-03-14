package com.ziyue.xuetang.utils;

import java.util.UUID;

public class UuidUtil {

	public static String getUuid() {

		String uid = UUID.randomUUID().toString().toLowerCase();

		uid = uid.replace("-", "");

		return uid;

	}

	public static String getUuidTakeLine() {

		String uid = UUID.randomUUID().toString().toLowerCase();
		return uid;
	}

	public static void main(String[] args) {

		System.out.println(getUuid());
	}

}
