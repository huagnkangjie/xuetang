package com.ziyue.xuetang.utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ziyue.xuetang.exception.ValidateException;


/**
 * String 处理类
 * 
 * @author Mr.H
 * 
 */
public class StringUtil {
	
	/**
	 * 检查给定String是否有值 无值返回TRUE
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string) {
		return ((string == null) || ("".equals(string)) || ("null".equals(string)));
	}

	/**
	 * 检查给定String是否有值 有值返回TRUE
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isNotEmpty(String string) {
		return ((string != null) && (string.length() > 0) && (!"null".equals(string)));
	}

	/**
	 * 返回由标识截取的字符串
	 * 
	 * @param sArg
	 *            需要截取的字符串
	 * @param cArg
	 *            截取标识
	 * @return
	 */
	public static String subString(String sArg, String cArg) {
		if (isEmpty(sArg))
			return "";
		if (isEmpty(cArg))
			return sArg;
		return sArg.indexOf(cArg) < 0 ? sArg : sArg.substring(0, sArg
				.indexOf(cArg));

	}
	/**
	 * 返回由标识截取的字符串
	 * 
	 * @param begin
	 *            开始
	 * @param end
	 *            结束
	 * @return
	 */
	public static String subString(String str,int begin, int end) {
		if(str==null){
			return "";
		}
		int length =str.length();
		if (begin<0)
			return "";
		if (end>=length)
			return str;
		return str.substring(begin,end);

	}
	
	/**
	 * 返回 由新字符串 替换 指定 字符串后的 结果 字符串 
	 * @param sArg
	 * @param oldChar
	 * @param newChar
	 * @return
	 */
	public static String replace(String sArg,String oldChar,String newChar){
		if(isEmpty(sArg))
			return null;
		if(isEmpty(oldChar))
			return sArg;
		return sArg.replace(oldChar, newChar);
	}

	/**
	 * 返回32位的随机UUID码
	 * 
	 * @return
	 */
	public static String getUuid() {
		try {
			return UUID.randomUUID().toString().replace("-", "");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据区划编码得到去掉所有右边为０的位后的前驱编码
	 * 
	 * @param qh
	 * @return
	 */
	public static String getRegion(String qh) {
		StringBuffer region = null;
		if (StringUtil.isEmpty(qh) || qh.equals("0"))
			return "5101";
		if (qh.length() == 1 && !qh.equals("0"))
			return qh;
		region = new StringBuffer();
		boolean flag = false;
		if (qh.length() % 2 != 0)
			flag = true;
		for (int i = 0; i < qh.length(); i = i + 2) {
			String sub = "";
			if (flag && i == qh.length() - 1) {
				sub = qh.substring(i, qh.length() - 1);
			} else {
				sub = qh.substring(i, i + 2);
			}
			if (sub.equals("00") || sub.equals("0"))
				break;
			region.append(sub);
		}
		return region.toString();
	}

	/**
	 * 返回当前时间 24小时制
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}

	

	  /**
     * 把给定的str转换为10进制表示的unicode
     * 目前只是用于mht模板的转码
     * @param str
     * @return
     */
    public static String encode2HtmlUnicode(String str) {
	    if(str == null) {
	    	return "";	
	    }
	    StringBuilder sb = new StringBuilder(str.length() * 2);
	    for (int i = 0; i < str.length(); i++) {
	        sb.append(encode2HtmlUnicode(str.charAt(i)));
	    }
	    return sb.toString();
    }
    public static String encode2HtmlUnicode(char character) {
        if (character > 255) {
            return "&#" + (character & 0xffff) + ";";
        } else {
            return String.valueOf(character);
        }
    }
    /**
	 * 将字符串进行转化为utf-8
	 * 
	 * @param str
	 * @return
	 */
    public static String toUtf8String(String s) { 
        StringBuffer sb = new StringBuffer(); 
        for (int i=0;i<s.length();i++) { 
            char c = s.charAt(i); 
            if (c >= 0 && c <= 255) { 
                sb.append(c); 
            } else { 
                byte[] b; 
                try { 
                    b = Character.toString(c).getBytes("utf-8"); 
                } catch (Exception ex) { 
                    b = new byte[0]; 
                } 
                for (int j = 0; j < b.length; j++) { 
                    int k = b[j]; 
                    if (k < 0) k += 256; 
                    sb.append("%" + Integer.toHexString(k).toUpperCase()); 
                } 
            } 
        } 
        return sb.toString(); 
    
    }
    
    /**
     * 去掉换行符空格
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String getLogo(String logo){
    	if(isEmpty(logo)){
    		logo = "";
    	}
    	return logo;
    }
    
    public static String getStringStr(String s){
    	String str = "";
    	if(isNotEmpty(s)){
    		str = s; 
    	}
    	return str;
    }
    
    /**
     * 获取名字
     * @param surname
     * @param missSurname
     * @return
     */
    public static String getResumeName(String surname, String missSurname){
    	surname = getStringStr(surname);
    	missSurname = getStringStr(missSurname);
    	return surname + missSurname;
    }
    
    /**
	 * 获取int型value
	 * @param map
	 * @param key
	 * @return
	 * @throws ValidateException
	 */
	public static int getIntValue(Map<String, Object> map, String key) throws ValidateException{
		
		return Integer.valueOf(String.valueOf(map.get(key)));
	}
	
	/**
	 * 获取String型value
	 * @param map
	 * @param key
	 * @return
	 * @throws ValidateException
	 */
	public static String getStringValue(Map<String, Object> map, String key) throws ValidateException{
		
		return String.valueOf(map.get(key));
	}
    
}

