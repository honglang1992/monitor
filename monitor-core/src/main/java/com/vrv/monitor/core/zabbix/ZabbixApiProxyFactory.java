package com.vrv.monitor.core.zabbix;

import com.zabbix4j.ZabbixApi;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ZabbixApiPorxy 工厂类
 * Created by Dendi on 2017/7/31.
 */
public class ZabbixApiProxyFactory {

    /**
     * 最大链接数
     */
    public static int maxActive = 10;

    /**
     * 工厂
     */
    public static Map<String, ZabbixApiProxy> factory = new LinkedHashMap<String, ZabbixApiProxy>(0, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, ZabbixApiProxy> eldest) {
            return size() > maxActive;
        }
    };

    public static ZabbixApiProxy getZabbixApiProxy(String apiUrl, String username, String password) {
        String key = apiUrl + username + password;
        if (factory.containsKey(key)) {
            return factory.get(key);
        }else{
            ZabbixApi zabbixApi = new ZabbixApi(apiUrl);
            ZabbixApiProxy zabbixApiProxy = new ZabbixApiProxy(zabbixApi,username,password);
            factory.put(key,zabbixApiProxy);
            return zabbixApiProxy;
        }
    }
}
