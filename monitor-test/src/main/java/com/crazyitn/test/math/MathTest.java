package com.crazyitn.test.math;

import java.math.BigDecimal;

import static java.lang.Math.E;

/**
 * @author dendi
 * @date 2018/7/10 17:06
 */
public class MathTest {

    public static  void main(String[] args){
        BigDecimal source = BigDecimal.ZERO;

        BigDecimal obj = source;
        obj = obj.add(BigDecimal.ONE);
        System.out.println(source);
        System.out.println(obj);

        double d1 = E-13;
        System.out.println(d1);
    }
}
