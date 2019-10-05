package com.vrv.monitor.core.jsonObject;

import com.alibaba.fastjson.JSONArray;

public class ArrayTest {
    public static void main(String[] args) {
        String item ="{\n" +
                "            \"TYPE\": \"E\",\n" +
                "                \"MESSAGE\": \"供应商 10100258: 没有银行键 10237，国家CN的银行主数据\"\n" +
                "        }";
        JSONArray itemArray = JSONArray.parseArray(item);
        System.out.println(itemArray);
    }
}
