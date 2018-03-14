package com.ziyue.xuetang.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年11月23日
 * @version v1.0.
 * 
 */
public class ValidateUtil {

	
//	private static String mailRegex = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
//	private static String mailRegex = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
	private static String mailRegex = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]{2,5}";
	
	/**
     * 正则表达式校验邮箱
     * @param emaile 待匹配的邮箱
     * @return 匹配成功返回true 否则返回false;
     */
	public static boolean validataEmaile(String emial){
        //正则表达式的模式
        Pattern p = Pattern.compile(mailRegex);
        //正则表达式的匹配器
        Matcher m = p.matcher(emial);
        //进行正则匹配
        return m.matches();
    }
	
	public static void main(String[] args) {
	    System.out.println(validataEmaile("382576884@qq.com"));
	}
}
