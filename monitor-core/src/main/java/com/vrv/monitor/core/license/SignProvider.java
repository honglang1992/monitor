package com.vrv.monitor.core.license;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by Dendi on 2018/1/16.
 */
public class SignProvider {
    public static boolean verify(String pubKey,String plainText,String signText) throws Exception {
        try{
            //解密由base64编码的公钥，并构造x509EncodeKeySpec对象
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(Base64.decode(pubKey));
            //RSA Key
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            //取公钥对象
            PublicKey publicKey = keyFactory.generatePublic(bobPubKeySpec);
            //解密由base64编码的数字签名
            byte[] signed = Base64.decode(signText);
            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initVerify(publicKey);
            signature.update(plainText.getBytes());
            //验证签名是否正常
            if(signature.verify(signed)){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            throw new Exception("校验签名异常",e);
        }
    }
}
