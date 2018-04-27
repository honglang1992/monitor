package com.vrv.monitor.datapicker.model.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dendi on 2018/1/12.
 */
public class CommonData {
    public final static Map<String,Product> productMsg ;

    static {
        productMsg = new HashMap<>();

        //访客机token
        String fkjToken = "38F59F0E-3295-4C14-89EF-E09D5333EC85";
        String fkjTokenPassword = "61F679D3-C478-4A8E-9C71-0DF22EC4BA22";
        Product fkj = new Product("访客机",fkjTokenPassword,"host");
        productMsg.put(fkjToken,fkj);
    }
}
