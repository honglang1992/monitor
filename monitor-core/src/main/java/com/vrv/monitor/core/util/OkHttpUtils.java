package com.vrv.monitor.core.util;


import okhttp3.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author horizon
 * @date 2017/12/5.
 */
public class OkHttpUtils {
    private OkHttpClient okHttpClient;

    private static OkHttpUtils okHttpUtils;

    private static final String CHARSET_NAME = "UTF-8";

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final HashMap<String, String> DEFAULT_HEADERS_MAP = new HashMap<String, String>() {
        {
            put("Accept", "application/json, text/javascript, */*; q=0.01");
            put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        }
    };

    private static final Headers DEFAULT_HEADERS = Headers.of(DEFAULT_HEADERS_MAP);

    public static OkHttpUtils getInstance() {
        if (null == okHttpUtils) {
            okHttpUtils = new OkHttpUtils();
        }
        return okHttpUtils;
    }

    private OkHttpUtils() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES));

        okHttpClient = builder.build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    /**
     * 同步get
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String get(String url, Headers headers) throws Exception {
        if (null == headers) {
            headers = DEFAULT_HEADERS;
        }

        Request request = new Request.Builder().headers(headers).url(url).build();

        Response response = execute(request);
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * 同步get请求
     *
     * @param url
     * @param data
     * @return
     * @throws Exception
     */
    public String get(String url, Headers headers, Map<String, String> data) throws Exception {
        url = getRequestUrl(url, data);

        if (null == headers) {
            headers = DEFAULT_HEADERS;
        }

        Request request = new Request.Builder().headers(headers).url(url).build();

        Response response = execute(request);
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * 异步get请求
     *
     * @param url
     * @param responseCallback
     * @return
     * @throws Exception
     */
    public void getAsync(String url, Callback responseCallback) throws Exception {
        Request request = new Request.Builder().url(url).build();

        enqueue(request, responseCallback);
    }

    /**
     * 同步post json数据
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();

        Response response = execute(request);
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * 同步post 键值对数据
     *
     * @param url
     * @param data
     * @return
     * @throws IOException
     */
    public String post(String url, Headers headers, Map<String, String> data) throws IOException {
        if (null == headers) {
            headers = DEFAULT_HEADERS;
        }
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> item : data.entrySet()) {
            formBuilder.add(item.getKey(), item.getValue());
        }

        RequestBody body = formBuilder.build();
        Request request = new Request.Builder().headers(headers).url(url).post(body).build();

        Response response = execute(request);
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * 异步post json
     *
     * @param url
     * @param json
     * @param responseCallback
     * @throws IOException
     */
    public void postAsync(String url, String json, Callback responseCallback) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();

        enqueue(request, responseCallback);
    }

    /**
     * 异步post key-value
     *
     * @param url
     * @param data
     * @param responseCallback
     * @throws IOException
     */
    public void postAsync(String url, Map<String, String> data, Callback responseCallback) throws IOException {
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> item : data.entrySet()) {
            formBuilder.add(item.getKey(), item.getValue());
        }

        RequestBody body = formBuilder.build();
        Request request = new Request.Builder().url(url).post(body).build();

        enqueue(request, responseCallback);
    }

    /**
     * 同步put
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public String put(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder().url(url).put(body).build();

        Response response = execute(request);
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * 同步put key-value
     *
     * @param url
     * @param data
     * @return
     * @throws IOException
     */
    public String put(String url, Map<String, String> data) throws IOException {
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> item : data.entrySet()) {
            formBuilder.add(item.getKey(), item.getValue());
        }

        RequestBody body = formBuilder.build();
        Request request = new Request.Builder().url(url).put(body).build();

        Response response = execute(request);
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * 异步put json
     *
     * @param url
     * @param json
     * @throws IOException
     */
    public void putAsync(String url, String json, Callback responseCallback) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder().url(url).put(body).build();
        enqueue(request, responseCallback);
    }

    /**
     * 异步put key-value
     *
     * @param url
     * @param data
     * @param responseCallback
     * @throws IOException
     */
    public void putAsync(String url, Map<String, String> data, Callback responseCallback) throws IOException {
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> item : data.entrySet()) {
            formBuilder.add(item.getKey(), item.getValue());
        }

        RequestBody body = formBuilder.build();
        Request request = new Request.Builder().url(url).put(body).build();

        enqueue(request, responseCallback);
    }

    /**
     * 通用同步请求。
     *
     * @param request
     * @return
     * @throws IOException
     */
    public Response execute(Request request) throws IOException {
        return okHttpClient.newCall(request).execute();
    }

    /**
     * 通用异步请求
     *
     * @param request
     * @param responseCallback
     */
    public void enqueue(Request request, Callback responseCallback) {
        okHttpClient.newCall(request).enqueue(responseCallback);
    }

    /**
     * 开启异步线程访问网络, 且不在意返回结果（实现空callback）
     *
     * @param request
     */
    public void enqueue(Request request) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public String getStringFromServer(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = execute(request);
        if (response.isSuccessful()) {
            String responseUrl = response.body().string();
            return responseUrl;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * get方式URL拼接
     *
     * @param
     * @param
     * @return
     */
    private static String getRequestUrl(String url, Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return url;
        } else {
            StringBuilder newUrl = new StringBuilder(url);
            if (url.indexOf("?") == -1) {
                newUrl.append("?rd=" + Math.random());
            }

            for (Map.Entry<String, String> item : map.entrySet()) {
                if (StringUtils.isNotBlank(item.getKey())) {
                    try {
                        newUrl.append("&" + item.getKey().trim() + "=" + URLEncoder.encode(String.valueOf(item.getValue().trim()), "UTF-8"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            return newUrl.toString();
        }
    }
}
