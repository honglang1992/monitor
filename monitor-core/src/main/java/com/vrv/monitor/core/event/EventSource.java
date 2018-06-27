package com.vrv.monitor.core.event;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 事件源
 * Created by Dendi on 2017/11/8.
 */
public class EventSource extends Observable{

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    public EventSource(List<Observer> observers){
        for(Observer observer : observers){
            this.addObserver(observer);
        }
    }

    public void addEvent(Object event){
        this.setChanged();
        this.notifyObservers(event);
    }

    public void addEventAsync(Object event){
        executorService.execute(()->{
            setChanged();
            notifyObservers(event);
        });
    }
}
