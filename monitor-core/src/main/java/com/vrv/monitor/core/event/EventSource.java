package com.vrv.monitor.core.event;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * 事件源
 * Created by Dendi on 2017/11/8.
 */
public class EventSource extends Observable{

    public EventSource(List<Observer> observers){
        for(Observer observer : observers){
            this.addObserver(observer);
        }
    }

    public void addEvent(Object event){
        this.setChanged();
        this.notifyObservers(event);
    }
}
