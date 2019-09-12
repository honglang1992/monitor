package com.vrv.monitor.core.map;


import com.zabbix4j.utils.json.JSONObject;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class MapUtil {
    public static void main(String[] args) {
//        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
//        queue.add("a");
//        queue.add("b");
//        queue.add("c");
//        queue.add("d");


        ConcurrentHashMap<String, String> maps = new ConcurrentHashMap<>();
        String a = "a";
        System.out.println(maps.putIfAbsent(a, "sfsfss"));
        System.out.println(maps.putIfAbsent(a,"secondvalue"));
        System.out.println(JSONObject.valueToString(maps));
    }
}
