package com.ziyue.xuetang.utils;

public class LogUtil {
	public static boolean mNeedLog = true;
	
	
	public static String TAG="XLog";
	
	

	
	public static void format(String format,Object... args){
		if (mNeedLog)
			System.out.format(format, args);
		
		
	}
	
	public static void println(String msg){
		if (mNeedLog)
			System.out.println(msg);
		
		
	}
	
	
}
