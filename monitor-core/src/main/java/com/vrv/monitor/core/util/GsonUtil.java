package com.vrv.monitor.core.util;

import com.google.gson.*;

import java.util.Map;
import java.util.Set;

/**
 * Created by Dendi on 2017/10/24.
 */
public class GsonUtil {
    public static Gson gson = new GsonBuilder().serializeNulls().create();

    /**
     * 把一个实体，按照指定字符串分割的形式输出
     * @param separator 分割字符串
     * @param data 实体
     */
    public static String splitModel(String separator,Object data){
        JsonObject returnData = new JsonParser().parse(gson.toJson(data)).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entrySet = returnData.entrySet();
        StringBuffer str_buff = new StringBuffer();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            JsonElement entryValue = entry.getValue();
            str_buff.append(entryValue.isJsonNull() ? "null":entryValue.getAsString());
            str_buff.append(separator);
        }
        String result = str_buff.substring(0, str_buff.length() - separator.length());
        return result;
    }
}
