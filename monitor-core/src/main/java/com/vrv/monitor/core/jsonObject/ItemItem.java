package com.vrv.monitor.core.jsonObject;

import com.alibaba.fastjson.annotation.JSONField;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class ItemItem{

	@JSONField(name="KUNNR")
	private String kUNNR;

	@JSONField(name="BUKRS")
	private String bUKRS;

	@JSONField(name="GJAHRS")
	private String gJAHRS;

	@JSONField(name="ZVJHNRS")
	private String zVJHNRS;

	@JSONField(name="GJAHRE")
	private String gJAHRE;

	public void setKUNNR(String kUNNR){
		this.kUNNR = kUNNR;
	}

	public String getKUNNR(){
		return kUNNR;
	}

	public void setBUKRS(String bUKRS){
		this.bUKRS = bUKRS;
	}

	public String getBUKRS(){
		return bUKRS;
	}

	public void setGJAHRS(String gJAHRS){
		this.gJAHRS = gJAHRS;
	}

	public String getGJAHRS(){
		return gJAHRS;
	}

	public void setZVJHNRS(String zVJHNRS){
		this.zVJHNRS = zVJHNRS;
	}

	public String getZVJHNRS(){
		return zVJHNRS;
	}

	public void setGJAHRE(String gJAHRE){
		this.gJAHRE = gJAHRE;
	}

	public String getGJAHRE(){
		return gJAHRE;
	}
}