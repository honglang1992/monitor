package com.crazyitn.test.map;

import com.crazyitn.test.stock.entity.StockVO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.E;

/**
 * @author dendi
 * @date 2018/9/6 14:35
 */
public class MapTest {
    //Map的get方法到底是 引用类型吗 答案是的 就是的
    public static  void main(String[] args){
        Map<Integer,StockVO> map = new HashMap<>();
        StockVO stockVO = new StockVO();
        stockVO.setName("test");
        map.put(1,stockVO);
        stockVO.setName("test2");
        StockVO stockVO1 = map.get(1);

        System.out.println(stockVO1.getName());
        stockVO1.setName("test3");

        System.out.println(map.get(1).getName());

    }
}
