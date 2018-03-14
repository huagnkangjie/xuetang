package com.ziyue.xuetang.common.secret;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Encoder;

public class DESCoderTest {

	private static byte[] decrypt(byte[] src, String password) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		// 创建一个DESKeySpec对象
		DESKeySpec desKey = new DESKeySpec(password.getBytes());
		// 创建一个密匙工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// 将DESKeySpec对象转换成SecretKey对象
		SecretKey securekey = keyFactory.generateSecret(desKey);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		// 真正开始解密操作
		return cipher.doFinal(src);
	}

	public static String desCrypto(byte[] datasource, String password) {
		try {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			DESKeySpec desKey = new DESKeySpec(password.getBytes());
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// 现在，获取数据并加密
			// 正式执行加密操作

			byte[] key_bs = cipher.doFinal(datasource);

			return (new BASE64Encoder()).encodeBuffer(key_bs);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String deZip(String str) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(out);
			gzip.write(str.getBytes());
			gzip.close();
			return out.toString("ISO-8859-1");
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	public static String unZip(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("utf-8"));
			GZIPInputStream gunzip = new GZIPInputStream(in);
			byte[] buffer = new byte[256];
			int n;
			while ((n = gunzip.read(buffer)) >= 0) {
				out.write(buffer, 0, n);
			}
			// toString()使用平台默认编码，也可以显式的指定如toString("GBK")
			return out.toString();
		} catch (Exception e) {

		}
		return null;
	}

	public static void decode() {
		try {
			// 待加密内容

			String str = "{\"expires_at\":\"2016-05-09 15:35:06\",\"uid\":'80991' }";

			// 密码，长度要是8的倍数
			String password = "kaipin11";

			String result =desCrypto(str.getBytes(), password );

			System.out.println("加密后内容为：" +  result );

			// 直接将如上内容解密

//			byte[] keys = new BASE64Decoder().decodeBuffer(result);
//
//			byte[] decryResult = decrypt(keys, password);
//
//			System.out.println("解密后内容为：" + unZip( new String(decryResult)));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public static void main(String[] args) {
		 decode();
		
	//	System.out.println(deZip("aafaf3dfafafafafedfafaa"));
		try {/*
				 * 
				 * String inputStr = "123456"; String key
				 * ="kaipin_!#%&(";//DESCoder.initKey();
				 * System.out.println("原文:\t" + inputStr);
				 * 
				 * System.out.println("密钥:\t" + key);
				 * 
				 * byte[] inputData = inputStr.getBytes();
				 * 
				 * inputData = DESCoder.encrypt(inputData, key);
				 * 
				 * System.out.println("加密后:\t" +
				 * DESCoder.encryptBASE64(inputData));
				 * 
				 * 
				 * 
				 * 
				 * byte[] outputData = DESCoder.decrypt(inputData, key);
				 * 
				 * String outputStr = new String(outputData);
				 * 
				 * System.out.println("解密后:\t" + outputStr);
				 */
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
