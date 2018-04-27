package com.vrv.monitor.core.util;


import org.apache.commons.lang3.CharEncoding;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Dendi on 2017/11/24.
 */
public class HttpUtil {
    private CloseableHttpClient httpClient;
    private static HttpUtil ourInstance = new HttpUtil();
    public static HttpUtil getInstance() {
        return ourInstance;
    }
    private HttpUtil() {
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setStaleConnectionCheckEnabled(true)
                .build();
        httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).setMaxConnTotal(100).setMaxConnPerRoute(100).build();
    }
    private CloseableHttpClient getHttpClient() {
        return this.httpClient;
    }
    /**
     * 发送get请求
     *
     * @param uri 请求地址
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public String get(String uri) throws HttpException, IOException {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            response = getHttpClient().execute(new HttpGet(uri));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity());
            } else {
                throw new HttpException(statusLine.toString());
            }
        } finally {
            try {
                if (entity != null) {
                    EntityUtils.consume(entity);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * get方式发送form表单
     *
     * @param uri        请求地址
     * @param parameters 表单参数
     * @return
     * @throws Exception
     */
    public String get(String uri, Map<String, ?> parameters) throws HttpException, IOException {
        return send(uri, parameters, HttpGet.METHOD_NAME);
    }
    /**
     * post方式发送form表单
     *
     * @param uri        请求地址
     * @param parameters 表单参数
     * @return
     * @throws Exception
     */
    public String post(String uri, Map<String, ?> parameters) throws HttpException, IOException {
        return send(uri, parameters, HttpPost.METHOD_NAME);
    }
    /**
     * post方式发送form表单
     *
     * @param uri        请求地址
     * @param parameters 表单参数
     * @return
     * @throws Exception
     */
    public String send(String uri, Map<String, ?> parameters, final String method) throws HttpException, IOException {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            RequestBuilder builder = RequestBuilder.create(method).setUri(uri);
            for (String k : parameters.keySet()) {
                Object v = parameters.get(k);
                if (v == null) {
                    builder.addParameter(k, "");
                } else {
                    builder.addParameter(k, v.toString());
                }
            }
            response = getHttpClient().execute(builder.build());
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity());
            } else {
                throw new HttpException(statusLine.toString());
            }
        } finally {
            try {
                if (entity != null) {
                    EntityUtils.consume(entity);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 根据request和uri获得响应信息,使用post方式获取,使用UTF-8编码
     *
     * @param uri    请求地址
     * @param bodyTxt http请求body内容
     * @return
     * @throws IOException
     */
    public String post(String uri, String bodyTxt) throws IOException, HttpException {
        return post(uri, bodyTxt, CharEncoding.UTF_8);
    }
    /**
     * 发送post请求
     *
     * @param uri      请求地址
     * @param bodyTxt  http请求body内容
     * @param encoding 编码类型
     * @return
     * @throws IOException
     * @throws HttpException
     */
    public String post(String uri, String bodyTxt, String encoding) throws IOException, HttpException {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            response = getHttpClient().execute(
                    RequestBuilder.post()
                            .setUri(uri)
                            .setEntity(new StringEntity(bodyTxt, encoding))
                            .build());
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                entity = response.getEntity();
                return EntityUtils.toString(entity);
            } else {
                throw new HttpException(statusLine.toString());
            }
        } finally {
            try {
                if (entity != null) {
                    EntityUtils.consume(entity);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
