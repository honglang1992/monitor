package com.crazyitn.test.thread;

public class RunableTest implements Runnable {
    private String name = "RunableTest";
    @Override
    public void run() {
        System.out.println(name + " is running");
    }
}
