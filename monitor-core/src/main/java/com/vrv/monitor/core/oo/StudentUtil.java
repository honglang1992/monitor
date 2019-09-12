package com.vrv.monitor.core.oo;


public class StudentUtil {
    public static void main(String[] args){
        Student a = new Student();
        a.setName("wang");
        Student b = a;

        a = new Student();

        System.out.println("a:"+ a.getName());
        System.out.println("b:"+ b.getName());
    }
}
