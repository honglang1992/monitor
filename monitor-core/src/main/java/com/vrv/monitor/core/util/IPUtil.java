package com.vrv.monitor.core.util;
import org.xbill.DNS.Address;

import java.net.InetAddress;
import java.net.UnknownHostException;
public class IPUtil {
    public static void main(String [] args) throws UnknownHostException {
        InetAddress netAddress = getInetAddress("10.10.35.10");
        System.out.println("host ip:" + getHostIp(netAddress));
        System.out.println("host name:" + getHostName(netAddress));
        String addr = Address.getHostName(netAddress);
        System.out.println("host name by dnsjava:" + addr);
        //

    }

    public static InetAddress getInetAddress(String ip){

        try{
            String[] ipStr = ip.split("\\.");
            byte[] ipBuf = new byte[4];
            for(int i = 0; i < 4; i++){
                ipBuf[i] = (byte)(Integer.parseInt(ipStr[i])&0xff);
            }
            return InetAddress.getByAddress(ipBuf);
        }catch(UnknownHostException e){
            System.out.println("unknown host!");
        }
        return null;

    }

    public static String getHostIp(InetAddress netAddress){
        if(null == netAddress){
            return null;
        }
        String ip = netAddress.getHostAddress(); //get the ip address

        return ip;
    }

    public static String getHostName(InetAddress netAddress){
        if(null == netAddress){
            return null;
        }
        String name = netAddress.getHostName(); //get the host address
        return name;
        }

}
