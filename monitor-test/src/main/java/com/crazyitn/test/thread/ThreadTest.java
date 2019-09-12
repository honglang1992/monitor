package com.crazyitn.test.thread;

public class ThreadTest {

    public static void main(String[] args){
        double begin = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            //循环
        }
        double end = System.currentTimeMillis();
        System.out.println("1万次循环所有的时间（s）"+((end-begin)/1000));
    }
}
