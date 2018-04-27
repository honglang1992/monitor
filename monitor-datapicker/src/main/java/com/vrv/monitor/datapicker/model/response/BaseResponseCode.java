package com.vrv.monitor.datapicker.model.response;

/**
 * Created by Dendi on 2018/1/12.
 */
public class BaseResponseCode {

    /**
     * 正常返回且有预期结果
     */
    public final static String SUCCESS = "0001";

    /**
     * 操作失败
     */
    public final static String FAILURE = "0002";


    /**
     * 参数缺失错误响应码
     */
    public final static String ERROR_PARAMETER_MISS = "0200";

    /**
     * 参数错误响应码
     */
    public final static String ERROR_PARAMETER_ERROR = "0201";

    /**
     * 参数格式错误响应码
     */
    public final static String ERROR_PARAMETER_FORMATER_ERROR = "0202";


    /**
     * 异常
     */
    public final static String EXCEPTION = "0300";

    /**
     * IO异常
     */
    public final static String EXCEPTION_IO = "0301";

    /**
     * JSON解析异常
     */
    public final static String EXCEPTION_JSON = "0302";

}