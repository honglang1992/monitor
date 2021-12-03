package com.vrv.monitor.core.jsonObject;

import com.alibaba.fastjson.annotation.JSONField;

import javax.annotation.Generated;
import java.util.List;

@Generated("com.robohorse.robopojogenerator")
public class IITEM{

	@JSONField(name="item")
	private List<ItemItem> item;

	public void setItem(List<ItemItem> item){
		this.item = item;
	}

	public List<ItemItem> getItem(){
		return item;
	}
}