package com.vrv.monitor.datapicker.common;

/**
 * Created by Dendi on 2017/10/13.
 */
public enum NetworkClass {

    VDN("VDN","视频网"),VPN("VPN","VPN网"),ITN("ITN","互联网"),PSN("PSN","公安网");

    private String value;

    private String description;

    private NetworkClass(String value,String description){
        this.value=value;
        this.description=description;
    }
    public String getValue(){
        return this.value;
    }

    public String getDescription(){
        return this.description;
    }

}
