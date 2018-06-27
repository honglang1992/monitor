package com.crazyitn.test.stock.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockVO {

    //@ApiModelProperty(value= "股票名称")
    private String name;

    //@ApiModelProperty(value= "今日开盘价")
    private Double todayOpenPrice;

    // @ApiModelProperty(value= "昨日收盘价")
    private Double yesterdayClosePrice;

    //@ApiModelProperty(value= "当前价格")
    private Double currentPrice;

    //@ApiModelProperty(value= "今日最高价")
    private Double todayHigh;

    //@ApiModelProperty(value= "今日最低价")
    private Double todayLow;

    //(实时价格-昨日收盘价)/昨日收盘价*100
    //@ApiModelProperty(value= "今日涨幅")
    private Double increasePer;
}
