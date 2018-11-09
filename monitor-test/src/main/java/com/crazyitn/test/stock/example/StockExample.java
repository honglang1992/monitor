package com.crazyitn.test.stock.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crazyitn.test.stock.entity.StockVO;
import com.vrv.monitor.core.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockExample {
    private static Logger logger = LoggerFactory.getLogger(StockExample.class);

    public static void main(String[] args) throws InterruptedException {
        while(true){
            try {
                StockVO stockVO = new StockVO();
                String addr = "http://hq.sinajs.cn/list=" + "sh500038";
                String apiResult = HttpUtil.getInstance().get(addr);
                logger.info("result:{}", JSON.toJSONString(apiResult));
                String[] splitResult = apiResult.split(",");
                stockVO.setName(splitResult[0].split("\"")[1]);
                stockVO.setTodayOpenPrice(Double.valueOf(splitResult[1]));
                stockVO.setYesterdayClosePrice(Double.valueOf(splitResult[2]));
                stockVO.setCurrentPrice(Double.valueOf(splitResult[3]));
                stockVO.setTodayHigh(Double.valueOf(splitResult[4]));
                stockVO.setTodayLow(Double.valueOf(splitResult[5]));

                if(stockVO.getCurrentPrice()==0 && stockVO.getYesterdayClosePrice()!=0){//处理接口在开盘之前的异常情况
                    stockVO.setCurrentPrice(stockVO.getYesterdayClosePrice());
                }

                Double increasePer = (stockVO.getCurrentPrice() - stockVO.getYesterdayClosePrice()) / stockVO.getYesterdayClosePrice() * 100;
                if(stockVO.getYesterdayClosePrice()==0){
                    stockVO.setIncreasePer(0d);
                }else{
                    stockVO.setIncreasePer(Double.valueOf(new java.text.DecimalFormat("#.00").format(increasePer)));
                }
                logger.info("数据:{}",JSONObject.toJSONString(stockVO));

            }catch (Exception ex){
                logger.error("股票实时接口出错",ex);
            }
            Thread.sleep(1000*5);
        }
    }
}
