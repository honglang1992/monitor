package com.vrv.monitor.core.license;


import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * Created by Dendi on 2018/1/16.
 */
public class Signaturer {
    /**
     * @param priKey  经过Base64编码的私钥
     * @param plainText 信息
     * @return 经过签名 并且 Base64的字符串
     * @throws Exception
     */
    public static String sign(String priKey,String plainText) throws Exception {
        try{
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(priKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyf.generatePrivate(priPKCS8);
            //用私钥对信息生成数字签名
            Signature signature = java.security.Signature.getInstance("MD5withRSA");
            signature.initSign(privateKey);
            signature.update(plainText.getBytes());
            String signed = Base64.encode(signature.sign()).replaceAll("\r|\n", "");
            return signed;
        }catch (Exception ex){
            throw new Exception("签名失败",ex);
        }
    }
}
