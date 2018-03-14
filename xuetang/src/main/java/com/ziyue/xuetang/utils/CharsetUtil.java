package com.ziyue.xuetang.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class CharsetUtil {

	public static String convert(String utfString) {

		if(utfString==null ||utfString.equals("")){
			return "";
		}
		
		
		utfString = utfString.toLowerCase();

		if (utfString.contains("\\u")) {
			return decodeUnicode(utfString);

		} else {

			return utfString;
		}
	}

	public static String encodeUnicode(final String gbString) {

		
		
		
		char[] utfBytes = gbString.toCharArray();
		String unicodeBytes = "";
		for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
			String hexB = Integer.toHexString(utfBytes[byteIndex]);
			if (hexB.length() <= 2) {
				hexB = "00" + hexB;
			}
			unicodeBytes = unicodeBytes + "\\u"+ hexB;
			
			
		}
	
		
		
		
		//System.out.println("unicodeBytes is: " + unicodeBytes);
		return unicodeBytes;
	}

	public static String decodeUnicode(String utfString) {

		
		utfString = utfString.toLowerCase();
		
		
		StringBuilder sb = new StringBuilder();
		int i = -1;
		int pos = 0;
		while ((i = utfString.indexOf("\\u", pos) ) != -1) {
			sb.append(utfString.substring(pos, i));
			if (i + 5 < utfString.length()) {
				pos = i + 6;
				sb.append((char) Integer.parseInt(
						utfString.substring(i + 2, i + 6), 16));
			}
		}

		return sb.toString();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(Constants.ProcessStatus.MsgType1.getValue());

		//System.out.println(decodeUnicode("{\"uname\":'\u0022\u003a\':\"pwd\":'123456'}"));
		
	//	System.out.println(decodeUnicode("\u0022"));
		  String s1 = "\u5e7f";
//System.out.println(encodeUnicode("是"));

System.out.println(JSON.toJSONString("\u4E2D\u56FD", SerializerFeature.BrowserCompatible)); 
		  
	}

}
