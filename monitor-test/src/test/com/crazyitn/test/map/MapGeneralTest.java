package com.crazyitn.test.map;


import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MapGeneralTest {

    @Test
    public void testMapSize(){
        Map<String,String> map = new HashMap<>();
        System.out.println(map.size());
    }
}