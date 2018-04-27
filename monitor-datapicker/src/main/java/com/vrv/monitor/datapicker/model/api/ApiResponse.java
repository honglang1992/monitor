package com.vrv.monitor.datapicker.model.api;

/**
 * Created by Dendi on 2018/1/11.
 */
public class ApiResponse {
    protected String state;

    protected String message;


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
