package com.vrv.monitor.core.jsonObject;

import com.alibaba.fastjson.annotation.JSONField;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Request{

	@JSONField(name="I_ITEM")
	private IITEM iITEM;

	public void setIITEM(IITEM iITEM){
		this.iITEM = iITEM;
	}

	public IITEM getIITEM(){
		return iITEM;
	}
}