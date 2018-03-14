package com.ziyue.xuetang.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/*
 * 编解码
 */
public class CodeUtil {

	public static String base64Encoder(byte[] data) {
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串

	}

	public static String base64Encoder(String data) {

		if (data == null || data.equals("")) {
			return null;
		}

		return base64Encoder(data.getBytes());

	}

	public static String base64ToString(String base64) {

		byte[] b = base64ToBytes(base64);

		if (b != null && b.length > 0) {

			return new String(b);
		}
		return null;
	}

	public static byte[] base64ToBytes(String base64) {

		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] bytes = decoder.decodeBuffer(base64);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			return bytes;
		} catch (Exception e) {
			return null;
		}

	}

}
