package com.vrv.monitor.core.license;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.security.*;


/**
 * Created by Dendi on 2018/1/16.
 */
public class KeyGenerater {
    private String priKey;

    private String pubKey;

    public void generater() throws NoSuchAlgorithmException {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed("vrv.bigdata".getBytes());
        keygen.initialize(1024,secureRandom);
        KeyPair keys = keygen.genKeyPair();
        PublicKey publicKey = keys.getPublic();
        PrivateKey privateKey = keys.getPrivate();
        pubKey = Base64.encode(publicKey.getEncoded()).replaceAll("\r|\n", "");
        priKey = Base64.encode(privateKey.getEncoded()).replaceAll("\r|\n", "");
    }

    public String getPriKey() {
        return priKey;
    }

    public void setPriKey(String priKey) {
        this.priKey = priKey;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }
}
