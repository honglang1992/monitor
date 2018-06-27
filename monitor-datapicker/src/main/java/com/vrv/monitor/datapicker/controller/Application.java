package com.vrv.monitor.datapicker.controller;

import com.vrv.monitor.core.jvm.StatusUtil;
import com.vrv.monitor.core.license.SignProvider;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Dendi on 2017/9/29.
 */
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) throws Exception {
        logger.warn("monitor-job Starting....");
        try{
            //由于 某些属性要在spring启动之前读取，所以这里需要加载properties文件
            Properties prop = new Properties();
            InputStream in = new BufferedInputStream(new FileInputStream(System.getProperty("user.dir") + "/conf/userConfig.properties"));
            prop.load(in);     //加载属性列表

            //验证license
            String pubKey = prop.getProperty("pubKey","");
            String license = prop.getProperty("license");
            String ip = prop.getProperty("ip");
            if(StringUtils.isNotBlank(pubKey) && StringUtils.isNotBlank(license) && StringUtils.isNotBlank(ip)){
                String macStr = StatusUtil.getMac(ip);
                boolean verifyResult = SignProvider.verify(pubKey,macStr,license);
                if(!verifyResult){
                    logger.error("证书验证失败 程序停止");
                    return;
                }
            }else{
                logger.error("证书不完整 程序停止");
                return;
            }

            //启动jetty
            String jettyPort = prop.getProperty("jetty.port","8080");
            Server server = JettySpringMVCStart.createServerIn(Integer.valueOf(jettyPort));
            server.start();
            server.join();
            logger.warn("monitor-job start successed");
        }catch (Exception ex){
            logger.warn("monitor-job start failed",ex);
        }catch (Throwable throwable){
            logger.error("Error",throwable);
        }
    }
}
