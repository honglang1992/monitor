package com.vrv.monitor.datapicker.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.vrv.monitor.core.util.MD5Util;
import com.vrv.monitor.datapicker.model.api.AssetMonitorInfo.AssetMonitorInfoPostRequest;
import com.vrv.monitor.datapicker.model.api.AssetMonitorInfo.AssetMonitorInfoPostResponse;
import com.vrv.monitor.datapicker.model.api.AssetMonitorInfo.AssetMonitorPeriodGetResponse;
import com.vrv.monitor.datapicker.model.api.BaseResponseCode;
import com.vrv.monitor.datapicker.model.api.CommonData;
import com.vrv.monitor.datapicker.model.api.Product;
import com.vrv.monitor.datapicker.service.api.AssetMonitorService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

/**
 * Created by Dendi on 2018/1/11.
 */
@RequestMapping("/api")
@Controller
public class AssetMonitorController {

    private static  final Logger logger = LoggerFactory.getLogger(AssetMonitorController.class);

    @Autowired
    private AssetMonitorService assetMonitorService;

    /**
     * 监控信息推送接口
     * @param request
     * @return
     */
    @RequestMapping(value = "/assetMonitorInfo", method = RequestMethod.POST)
    @ResponseBody
    public AssetMonitorInfoPostResponse assetMonitorInfo(HttpServletRequest request) {
        AssetMonitorInfoPostResponse response = new AssetMonitorInfoPostResponse();
        try {
            Product product = checkHeaders(request);
            BufferedReader br = request.getReader();
            String str, wholeStr = "";
            while((str = br.readLine()) != null){
                wholeStr += str;
            }
            checkRequestBodyData(wholeStr);
            response.setState(BaseResponseCode.SUCCESS);
            response.setMessage("成功");
        } catch (Exception ex) {
            response.setState(BaseResponseCode.FAILURE);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    /**
     * 监控频率 获取接口
     * @return
     */
    @RequestMapping(value = "/assetMonitorPeriod", method = RequestMethod.GET)
    @ResponseBody
    public AssetMonitorPeriodGetResponse assetMonitorPeroid(HttpServletRequest request) {
        AssetMonitorPeriodGetResponse response = new AssetMonitorPeriodGetResponse();
        try {
            Product product = checkHeaders(request);
            response.setState(BaseResponseCode.SUCCESS);
            response.setMessage("成功");
            String period = assetMonitorService.getMonitorPeriodBySuitName(product.getSuitName());
            response.setPeriod(period);
        } catch (Exception ex) {
            response.setState(BaseResponseCode.FAILURE);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    private Product checkHeaders(HttpServletRequest request) throws Exception {
        Product product;
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new Exception("请求令牌缺失");
        }

        String timestamp = request.getHeader("timestamp");
        if (StringUtils.isEmpty(timestamp)) {
            throw new Exception("请求时间戳缺失");
        }

        String digitalSign = request.getHeader("digitalSign");
        if (StringUtils.isEmpty(digitalSign)) {
            throw new Exception("签名字符串缺失");
        }

        String version = request.getHeader("version");
        if (StringUtils.isEmpty(version) || !"1.0".equals(version) ) {
            throw new Exception("接口版本缺失或错误");
        }

        if (CommonData.productMsg.containsKey(token)) {
            product = (Product) CommonData.productMsg.get(token);
        } else {
            throw new Exception("请求令牌错误");
        }
        String txt = String.format("%s#%s#%s", token, CommonData.productMsg.get(token).getTokenPassword(), timestamp);

        String expectedSignature = MD5Util.getMD5(txt);
        if (!expectedSignature.equals(digitalSign)) {
            throw new Exception("签名字符串错误");
        }

        return product;
    }

    private void  checkRequestBodyData(String requestBody) throws Exception {
        try{
            logger.debug("rquest body : {}",requestBody);
            JSONArray body = JSONArray.parseArray(requestBody);
            if(body.size()>100){
                throw new Exception("监控条数不得超过100条");
            }
            //TODO 解析每个实体 并传输
        }catch (Exception ex){
            throw new Exception("数据格式错误");
        }
    }
}
