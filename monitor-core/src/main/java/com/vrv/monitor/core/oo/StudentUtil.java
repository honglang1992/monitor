package com.vrv.monitor.core.oo;


import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentUtil {
    public static void main(String[] args){
        List<Student> list = new ArrayList<>();
        Student a = new Student();
        a.setName("wang");
        list.add(a);
        List<Student> list2 = list;

        list = new ArrayList<>();

        System.out.println("list:"+ JSONObject.toJSONString(list));
        System.out.println("list2:"+ JSONObject.toJSONString(list2));
    }
}
