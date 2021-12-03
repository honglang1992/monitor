package com.vrv.monitor.core.jsonObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vrv.monitor.core.jsonObject.response.Response;

import java.util.ArrayList;

public class ArrayTest {
    public static void main(String[] args) {
        String ids = "1,3,4,";
        String[] idStr = ids.split(",");
        System.out.println(idStr);
    }
}
