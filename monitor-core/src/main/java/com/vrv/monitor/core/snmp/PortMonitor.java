package com.vrv.monitor.core.snmp;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Dendi on 2017/10/24.
 */
public class PortMonitor {
    public static boolean testPort(String ip ,int port){
        boolean result ;
        try {
            Socket socket = new Socket(ip,port);
            result = socket.isConnected();
            socket.close();
        } catch (IOException e) {
            result = false;
        }
        return result;
    }
}
