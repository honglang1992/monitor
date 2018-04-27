package com.vrv.monitor.core.util;

import org.apache.http.HttpException;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Dendi on 2017/11/29.
 */
public class HttpUtilTest {

    @Test
    public void testGet(){
        try {
            String response = HttpUtil.getInstance().get("http://22.70.100.162:60009/api/DeviceStatus/GetDs?laneNo=6528240100004-CZHY02");
            System.out.println("responseStr:"+response);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGuid(){
        for(int i = 0 ; i<20;i++){
            System.out.println(UUIDUtil.get32UUID());
        }
    }
}
