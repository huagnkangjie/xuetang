package com.ziyue.xuetang.utils;
/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年11月29日
 * @version v1.0.
 * 
 */
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AesEncryptUtil {

    //使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同！
    private static String KEY = "dufy20170329java";

    private static String IV = "dufy20170329java";


    /**
     * 加密方法
     * @param data  要加密的数据
     * @param key 加密key
     * @param iv 加密iv
     * @return 加密的结果
     * @throws Exception
     */
    public static String encrypt(String data, String key, String iv) throws Exception {
        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");//"算法/模式/补码方式"
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return new Base64().encodeToString(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密方法
     * @param data 要解密的数据
     * @param key  解密key
     * @param iv 解密iv
     * @return 解密的结果
     * @throws Exception
     */
    public static String desEncrypt(String data, String key, String iv) throws Exception {
        try {
            byte[] encrypted1 = new Base64().decode(data);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用默认的key和iv加密
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        return encrypt(data, KEY, IV);
    }

    /**
     * 使用默认的key和iv解密
     * @param data
     * @return
     * @throws Exception
     */
    public static String desEncrypt(String data) throws Exception {
        return desEncrypt(data, KEY, IV);
    }



    /**
    * 测试
    */
    public static void main(String args[]) throws Exception {

        String test = "123";

        String data = null;
        String key = "iwanli";
        String iv = "iwanli";

        data = encrypt(test, key, iv);

        System.out.println(data);
        System.out.println(desEncrypt("U2FsdGVkX19VNyhYk++DGTY7hZCAxl3qMabBGSEAq8XO+9Lyz4KJBv2emWTtaybSFSUp1y0GNVZ7LqPwW74F41RfV5nlO/voNCa5FeMf7miQrWzG+J9ISTKu2glnZkDpQJwMFlWlbhF/oFys7RagRr88+F+ZJ7bqacrqrGoutS37Sx1mxnlWBaN71tIQr5Uwp6MYrIT+3Ste/p2pHEO28bi0jiWAaGScttv1TA1IK8iPXhEj1tglarNn5d8iSjw+cZZiyj8/p4SXXt/V+z5On0bl9XfPHtdCNjWJxXo2xaAtpS6Eqeckq0HBSG/2UFWdCIxTgdSGN5FfYvafOQd1qZoj0CQUmhpWCjJCn3gIp+qIlNkkn0z42des33j9hgXUqBSehQFHaKMCwh+DvIIJlFCtcR0O8CW/GEniwzQRr3nR43dYfVtCFd5gKcI5137vVFhdZheeQ5F6r+2+RKtG0FFV0nNoTSEPk/0wspCFBTv5WOw5WY7fKVDU1JtTuo2SCSgp961TLxYMZ4uNK30wLlgpNV61tH0LUUUp5qzriaECnVKG+UY85znkCpCam8GuNybogg6NNLDSdq/lXE97Nd+bz77h5VYuKm/gmVaLhmbfBoB9psY5/LRJjcPIA4oVCb6qt1ABwiQ+4vUmquKOLDzLJBgqwfewMf4Wiq0/IDko44R5gceGkmcEOmWRuRhPd0yiL1Q+QcobN6ar1rN+XQZIkg7EdMcTtMEBaUNvkWg=", key, iv));
    }

}
