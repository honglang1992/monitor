package com.vrv.monitor.core;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class encode {
    //123456------>0000000000000000043db1f2ceac91f2913c6c45c0e6bdf13f235e50
    //产生一个md5加密后的密码
    public static void main(String[] args) {
        String hashAlgorithmName = "SHA-1";                                                 //使用SHA-1算法
        char[] credentials = new char[6];                                                   //前台传过来的原始密码
        credentials[0]='1';
        credentials[1]='2';
        credentials[2]='3';
        credentials[3]='4';
        credentials[4]='5';
        credentials[5]='6';
        String databasepassword="0000000000000000043db1f2ceac91f2913c6c45c0e6bdf13f235e50"; //数据库中的加密过的密码
        /*  byte[] salt1 = Encodes.decodeHex(databasepassword.substring(0,16));*/
        byte[] salt1=new byte[8];
        for (byte b : salt1) {
            b=0;
        }
        ByteSource salt = ByteSource.Util.bytes(salt1);                                     //盐
        int hashIterations = 1024;                                                          //加密次数
        Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        String result2="0000000000000000"+result.toString();
        System.out.println(result2);
        if(result2.equals(databasepassword)){
            System.out.println("密码正确");
        }else {
            System.out.println("密码错误");
        }
    }
}
