package com.vrv.monitor.core.jsonObject.response;

import com.alibaba.fastjson.annotation.JSONField;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Response{

	@JSONField(name="I_ITEM")
	private String iITEM;

	@JSONField(name="T_RETURN")
	private TRETURN tRETURN;

	@JSONField(name="E_STATUS")
	private String eSTATUS;

	public void setIITEM(String iITEM){
		this.iITEM = iITEM;
	}

	public String getIITEM(){
		return iITEM;
	}

	public void setTRETURN(TRETURN tRETURN){
		this.tRETURN = tRETURN;
	}

	public TRETURN getTRETURN(){
		return tRETURN;
	}

	public void setESTATUS(String eSTATUS){
		this.eSTATUS = eSTATUS;
	}

	public String getESTATUS(){
		return eSTATUS;
	}
}