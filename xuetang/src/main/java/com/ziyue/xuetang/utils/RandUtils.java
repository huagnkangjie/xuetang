
package com.ziyue.xuetang.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;


public class RandUtils {

    public static final Integer ENGLISH = 1;
    public static final Integer ENGLISH_UPPER_CASE = 2;
    public static final Integer ENGLISH_LOWER_CASE = 3;
    public static final Integer NUMBER = 4;
    public static final Integer ENGLISH_NUMBER = 5;
    public static final Integer ENGLISH_UPPER_CASE_NUMBER = 6;
    public static final Integer ENGLISH_LOWER_CASE_NUMBER = 7;
    public static final Integer NUMBER_FIRST_CHARACTER_NOT_ZERO = 8;
    public static final Integer NUMBER_SPECIFIC_ACCOUNT = 9;

    private static final String BASE_ENGLISH = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
    private static final String BASE_ENGLISH_UPPER_CASE = "QWERTYUIOPASDFGHJKLZXCVBNM";
    private static final String BASE_ENGLISH_LOWER_CASE = "qwertyuiopasdfghjklzxcvbnm";
    private static final String BASE_NUMBER = "0123456789";
    private static final String BASE_ENGLISH_NUMBER = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm0123456789";
    private static final String BASE_ENGLISH_UPPER_CASE_NUMBER = "QWERTYUIOPASDFGHJKLZXCVBNM0123456789";
    private static final String BASE_ENGLISH_LOWER_CASE_NUMBER = "qwertyuiopasdfghjklzxcvbnm0123456789";


    /**
     * generateion random string
     * 
     * @param length
     * @param type
     * @return
     */
    public static String generationRandomString(Integer length, Integer type) {
        String base = "";// wxx Debug ：防�?null pointer wxx
        if (type.intValue() == NUMBER_SPECIFIC_ACCOUNT) {
            return generationRandomAccount();
        }
        switch (type) {
        case 1: {
            base = BASE_ENGLISH;
            break;
        }
        case 2: {
            base = BASE_ENGLISH_UPPER_CASE;
            break;
        }
        case 3: {
            base = BASE_ENGLISH_LOWER_CASE;
            break;
        }
        case 4: {
            base = BASE_NUMBER;
            break;
        }
        case 5: {
            base = BASE_ENGLISH_NUMBER;
            break;
        }
        case 6: {
            base = BASE_ENGLISH_UPPER_CASE_NUMBER;
            break;
        }
        case 7: {
            base = BASE_ENGLISH_LOWER_CASE_NUMBER;
            break;
        }
        case 8: {
            base = BASE_NUMBER;
            break;
        }
        }
        int size = base.length();
        StringBuffer result = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = base.charAt(random.nextInt(size));
            if (type == 8 && i == 0 && c == '0') {
                while (c == '0') {
                    c = base.charAt(random.nextInt(size));
                }
            }
            result.append(c);
        }
        return result.toString();
    }

    /**
     * generateion account create account number and filter specific characters
     * 
     * @return
     */
    private static String generationRandomAccount() {

        int length = 6;
        List<String> filterCharactersArray = null;
        List<String> replaceCharactersArray = null;
        String accountPrefix = "11";
        if (length == 0) {
            length = 6;
        }
        if (filterCharactersArray == null || filterCharactersArray.size() == 0) {
            filterCharactersArray = new ArrayList<String>();
            filterCharactersArray.add("8");
            filterCharactersArray.add("11");
            filterCharactersArray.add("66");
            filterCharactersArray.add("77");
            filterCharactersArray.add("99");
            filterCharactersArray.add("55");
        }
        if (replaceCharactersArray == null || replaceCharactersArray.size() == 0) {
            replaceCharactersArray = new ArrayList<String>();
            replaceCharactersArray.add("4");
            replaceCharactersArray.add("14");
            replaceCharactersArray.add("64");
            replaceCharactersArray.add("74");
            replaceCharactersArray.add("94");
            replaceCharactersArray.add("54");
        }
        if (StringUtils.isEmpty(accountPrefix)) {
            accountPrefix = "13";
        }
        String account = generationRandomString(length, NUMBER_FIRST_CHARACTER_NOT_ZERO);
        StringBuffer accountBuffer = new StringBuffer(account);

        for (int i = 0; i < filterCharactersArray.size(); i++) {
            String filter = filterCharactersArray.get(i);
            String replace = replaceCharactersArray.get(i);
            int start = accountBuffer.indexOf(filter);
            if (start != -1) {
                accountBuffer.replace(start, start + filter.length(), replace);
            }
        }

        return accountPrefix + accountBuffer.toString();
    }
    
    
	public static void main(String[] args) {

		System.out.println(generationRandomAccount() );
	}

}
