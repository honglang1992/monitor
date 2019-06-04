package com.crazyitn.test.math;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.*;

public class BigDecimalUtilTest {
    @Test
    public void testScole(){
        BigDecimal b1 = new BigDecimal("0.33333333");
        System.out.println(b1);
        b1= b1.setScale(10,RoundingMode.DOWN);
        System.out.println(b1);
    }

}