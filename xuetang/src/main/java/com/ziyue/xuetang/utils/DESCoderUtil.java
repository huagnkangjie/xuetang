package com.ziyue.xuetang.utils;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.springframework.util.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESCoderUtil {
	
	public static final String password_key = "ziyuen_!#%&(";
	public static final String password ="888888";
	
	/**
	 * 加密
	 * @param src
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] src, String password) throws Exception {  
        // DES算法要求有一个可信任的随机数源  
        SecureRandom random =  SecureRandom.getInstance("SHA1PRNG");  
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
	
	/**
	 * 解密
	 * @param datasource
	 * @param password
	 * @return
	 */
    public static String desCrypto(byte[] datasource, String password) {              
        try{  
        SecureRandom random =  SecureRandom.getInstance("SHA1PRNG");  
        DESKeySpec desKey = new DESKeySpec(password.getBytes());  
        //创建一个密匙工厂，然后用它把DESKeySpec转换成  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
        SecretKey securekey = keyFactory.generateSecret(desKey);  
        //Cipher对象实际完成加密操作  
        Cipher cipher = Cipher.getInstance("DES");  
        //用密匙初始化Cipher对象  
        cipher.init(Cipher.ENCRYPT_MODE, securekey, random);  
        //现在，获取数据并加密  
        //正式执行加密操作  
        
        byte[] key_bs= cipher.doFinal(datasource);  
        
        return (new BASE64Encoder()).encodeBuffer(key_bs);
        
        }catch(Throwable e){  
                e.printStackTrace();  
        }  
        return null;  
    }  
    
    
    /**
     * 加密
     * 
     * 如果加密内容为空，则默认加密 888888
     * @param pw
     * @return
     */
    public static String deCode(String pw){
    	String result = "";
    	if(!StringUtils.isEmpty(pw)){
    		result = desCrypto(pw.getBytes(),password_key);
    	}else{
    		result = desCrypto(password.getBytes(),password_key);
    	}
    	return result;
    }
    
    /**
     * 解密
     * @param pw
     * @return
     */
    public static String enCode(String pw){
    	try {
       	 	byte[] keys=     new BASE64Decoder().decodeBuffer(pw);
   		    byte[] decryResult =  decrypt(keys, password_key);
   		    return new String(decryResult);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
	
	public static void main(String[] args) {
		System.out.println(deCode("181206"));
		System.out.println(">>>>>>"+enCode("olfZX9MYJq0="));
	}

}
