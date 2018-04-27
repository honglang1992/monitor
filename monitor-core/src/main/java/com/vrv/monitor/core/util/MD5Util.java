package com.vrv.monitor.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Dendi on 2018/1/12.
 */
public class MD5Util {

    private final static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getMD5(String txt) throws NoSuchAlgorithmException {
        byte[] btInput = txt.getBytes();
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(btInput);
        byte[] md = digest.digest();
        int len = md.length;
        int k = 0;
        char tag[] = new char[len * 2];
        for (int i = 0; i < len; i++) {
            byte t = md[i];
            tag[k++] = hexDigits[t >>> 4 & 0xf];
            tag[k++] = hexDigits[t & 0xf];
        }
        return new String(tag);
    }
}