package com.ziyue.xuetang.common.message;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import com.ziyue.xuetang.utils.PropertyUtils;

public class Messages {

    private static final String BUNDLE_NAME =
            "/com/ziyue/xuetang/common/message/messages_zh_CN.properties";

    // private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
    // .getBundle(BUNDLE_NAME,new Locale("zh_CN"));


    private static Properties RESOURCE_BUNDLE = loadProperties();

    private static Properties loadProperties() {
        String filePath = BUNDLE_NAME;
        InputStream inputStream = PropertyUtils.class.getResourceAsStream(filePath);
        if (null == inputStream) {
            return null;
        }
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {

        }
        return properties;


    }



    private Messages() {}

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getProperty(key);
        } catch (Exception e) {
            return '!' + key + '!';
        }
    }

    public static String getString(String key, String parm1) {
        try {
            return MessageFormat.format(RESOURCE_BUNDLE.getProperty(key), new Object[] {parm1});
        } catch (Exception e) {
            return '!' + key + '!';
        }
    }

    public static String getString(String key, String parm1, String parm2) {
        try {
            return MessageFormat.format(RESOURCE_BUNDLE.getProperty(key),
                    new Object[] {parm1, parm2});


        } catch (Exception e) {
            return '!' + key + '!';
        }
    }

    public static String getString(String key, String parm1, String parm2, String parm3) {
        try {
            return MessageFormat.format(RESOURCE_BUNDLE.getProperty(key),
                    new Object[] {parm1, parm2, parm3});
        } catch (Exception e) {
            return '!' + key + '!';
        }
    }

    public static String getString(String key, String parm1, String parm2, String parm3,
            String parm4) {
        try {
            return MessageFormat.format(RESOURCE_BUNDLE.getProperty(key),
                    new Object[] {parm1, parm2, parm3, parm4});
        } catch (Exception e) {
            return '!' + key + '!';
        }
    }

    public static String getStrings(String key, String... parms) {

        try {

            if (parms == null || parms.length == 0) {

                return null;
            }

            Object obj[] = new Object[parms.length];

            for (int i = 0; i < parms.length; i++) {
                obj[i] = parms[i];
            }

            return MessageFormat.format(RESOURCE_BUNDLE.getProperty(key), obj);
        } catch (Exception e) {
            return '!' + key + '!';
        }

    }

    public static void main(String[] args) {


        // System.out.println(RESOURCE_BUNDLE.getProperty("10001"));
        System.out.println(getStrings("10004", "test"));
    }

}
