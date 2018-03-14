package com.ziyue.xuetang.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaUtil {

	public static String sha1(String data) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("SHA1");

		md.update(data.getBytes());

		StringBuffer buf = new StringBuffer();

		byte[] bits = md.digest();

		for (int i = 0; i < bits.length; i++) {

			int a = bits[i];

			if (a < 0)
				a += 256;

			if (a < 16)
				buf.append("0");

			buf.append(Integer.toHexString(a));

		}

		return buf.toString();

	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException{
		
		   System.out.println("SHA1 : "+sha1("0e91b98e-8362-11e5-861e-240a64641bf6").length());
	}
}
