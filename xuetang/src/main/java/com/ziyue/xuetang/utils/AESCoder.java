package com.ziyue.xuetang.utils;
/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年11月29日
 * @version v1.0.
 * 
 */

	import java.math.BigInteger;  
	  
	import javax.crypto.Cipher;  
	import javax.crypto.KeyGenerator;  
	import javax.crypto.spec.SecretKeySpec;  
	  
	import org.apache.commons.codec.binary.Base64;  
	import org.apache.commons.lang3.StringUtils;  
	  
	import sun.misc.BASE64Decoder;  
	  
	/** 
	 * 编码工具类 
	 * 实现aes加密、解密 
	 */  
	public class AESCoder {  
	      
	    /** 
	     * 密钥 
	     */  
	    private static final String KEY = "iwanli";  
	      
	    /** 
	     * 算法 
	     */  
	    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";  
	  
	    public static void main(String[] args) throws Exception {  
	        String content = "我爱你";  
	        System.out.println("加密前：" + content);  
	  
	        System.out.println("加密密钥和解密密钥：" + KEY);  
	  
	        String encrypt = aesEncrypt(content, KEY);  
	        System.out.println("加密后：" + encrypt);  
	  
	        String decrypt = aesDecrypt("U2FsdGVkX19VNyhYk++DGTY7hZCAxl3qMabBGSEAq8XO+9Lyz4KJBv2emWTtaybSFSUp1y0GNVZ7LqPwW74F41RfV5nlO/voNCa5FeMf7miQrWzG+J9ISTKu2glnZkDpQJwMFlWlbhF/oFys7RagRr88+F+ZJ7bqacrqrGoutS37Sx1mxnlWBaN71tIQr5Uwp6MYrIT+3Ste/p2pHEO28bi0jiWAaGScttv1TA1IK8iPXhEj1tglarNn5d8iSjw+cZZiyj8/p4SXXt/V+z5On0bl9XfPHtdCNjWJxXo2xaAtpS6Eqeckq0HBSG/2UFWdCIxTgdSGN5FfYvafOQd1qZoj0CQUmhpWCjJCn3gIp+qIlNkkn0z42des33j9hgXUqBSehQFHaKMCwh+DvIIJlFCtcR0O8CW/GEniwzQRr3nR43dYfVtCFd5gKcI5137vVFhdZheeQ5F6r+2+RKtG0FFV0nNoTSEPk/0wspCFBTv5WOw5WY7fKVDU1JtTuo2SCSgp961TLxYMZ4uNK30wLlgpNV61tH0LUUUp5qzriaECnVKG+UY85znkCpCam8GuNybogg6NNLDSdq/lXE97Nd+bz77h5VYuKm/gmVaLhmbfBoB9psY5/LRJjcPIA4oVCb6qt1ABwiQ+4vUmquKOLDzLJBgqwfewMf4Wiq0/IDko44R5gceGkmcEOmWRuRhPd0yiL1Q+QcobN6ar1rN+XQZIkg7EdMcTtMEBaUNvkWg=", KEY);  
	        System.out.println("解密后：" + decrypt);  
	    }  
	      
	    /** 
	     * aes解密 
	     * @param encrypt   内容 
	     * @return 
	     * @throws Exception 
	     */  
	    public static String aesDecrypt(String encrypt) throws Exception {  
	        return aesDecrypt(encrypt, KEY);  
	    }  
	      
	    /** 
	     * aes加密 
	     * @param content 
	     * @return 
	     * @throws Exception 
	     */  
	    public static String aesEncrypt(String content) throws Exception {  
	        return aesEncrypt(content, KEY);  
	    }  
	  
	    /** 
	     * 将byte[]转为各种进制的字符串 
	     * @param bytes byte[] 
	     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制 
	     * @return 转换后的字符串 
	     */  
	    public static String binary(byte[] bytes, int radix){  
	        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数  
	    }  
	  
	    /** 
	     * base 64 encode 
	     * @param bytes 待编码的byte[] 
	     * @return 编码后的base 64 code 
	     */  
	    public static String base64Encode(byte[] bytes){  
	        return Base64.encodeBase64String(bytes);  
	    }  
	  
	    /** 
	     * base 64 decode 
	     * @param base64Code 待解码的base 64 code 
	     * @return 解码后的byte[] 
	     * @throws Exception 
	     */  
	    public static byte[] base64Decode(String base64Code) throws Exception{  
	        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);  
	    }  
	  
	      
	    /** 
	     * AES加密 
	     * @param content 待加密的内容 
	     * @param encryptKey 加密密钥 
	     * @return 加密后的byte[] 
	     * @throws Exception 
	     */  
	    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {  
	        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
	        kgen.init(128);  
	        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);  
	        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));  
	  
	        return cipher.doFinal(content.getBytes("utf-8"));  
	    }  
	  
	  
	    /** 
	     * AES加密为base 64 code 
	     * @param content 待加密的内容 
	     * @param encryptKey 加密密钥 
	     * @return 加密后的base 64 code 
	     * @throws Exception 
	     */  
	    public static String aesEncrypt(String content, String encryptKey) throws Exception {  
	        return base64Encode(aesEncryptToBytes(content, encryptKey));  
	    }  
	  
	    /** 
	     * AES解密 
	     * @param encryptBytes 待解密的byte[] 
	     * @param decryptKey 解密密钥 
	     * @return 解密后的String 
	     * @throws Exception 
	     */  
	     public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {  
	            KeyGenerator kgen = KeyGenerator.getInstance("AES");  
	            kgen.init(128);  
	  
	            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);  
	            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));  
	            byte[] decryptBytes = cipher.doFinal(encryptBytes);  
	  
	            return new String(decryptBytes);  
	        }  
	  
	  
	    /** 
	     * 将base 64 code AES解密 
	     * @param encryptStr 待解密的base 64 code 
	     * @param decryptKey 解密密钥 
	     * @return 解密后的string 
	     * @throws Exception 
	     */  
	    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {  
	        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);  
	    }  
	  
	} 