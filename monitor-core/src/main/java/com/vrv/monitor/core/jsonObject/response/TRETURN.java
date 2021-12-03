package com.vrv.monitor.core.jsonObject.response;

import com.alibaba.fastjson.annotation.JSONField;

import javax.annotation.Generated;
import java.util.List;

@Generated("com.robohorse.robopojogenerator")
public class TRETURN{

	@JSONField(name="item")
	private List<ItemItem> item;

	public void setItem(List<ItemItem> item){
		this.item = item;
	}

	public List<ItemItem> getItem(){
		return item;
	}
}