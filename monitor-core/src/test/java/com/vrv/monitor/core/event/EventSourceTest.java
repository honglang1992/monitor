package com.vrv.monitor.core.event;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

public class EventSourceTest {
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    public void addEvent(Integer a) {
        System.out.println("addEvent method execued" + a);
    }

    public void addEventAsync(Integer a) {
        System.out.println(a);
        executorService.execute(() -> {
            addEvent(a);
        });
        System.out.println("end");
    }

    @Test
    public void testMain(){
        addEventAsync(3);

    }

}