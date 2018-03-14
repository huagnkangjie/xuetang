package com.tencent;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class SigMessages {

	private static final String BUNDLE_NAME = "com/tencent/messages";

	public static final String EXPIRE = "expire";

	public static final String APPID3RD = "appid3rd";
	public static final String SdKAPPID = "skdAppid";
	public static final String ACCOUNTTYPE = "accountType";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME, new Locale("zh_CN"));

	private SigMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static long getLong(String key) {
		try {

			String v = RESOURCE_BUNDLE.getString(key);

			if (v != null) {

				return Long.parseLong(v);

			}

		} catch (MissingResourceException e) {

		}
		return 0;
	}

	

	public static void main(String[] args) {

		System.out.println(getString(EXPIRE));
	}

}
