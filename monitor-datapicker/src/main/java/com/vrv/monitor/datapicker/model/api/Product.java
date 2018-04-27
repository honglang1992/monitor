package com.vrv.monitor.datapicker.model.api;

/**
 * Created by Dendi on 2018/1/12.
 */
public class Product {
    private String productName;

    private String tokenPassword;

    /**
     * 监控频率key 用来查改设备的监控频率
     */
    private String suitName;

    public Product(String productName, String tokenPassword, String suitName) {
        this.productName = productName;
        this.tokenPassword = tokenPassword;
        this.suitName = suitName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTokenPassword() {
        return tokenPassword;
    }

    public void setTokenPassword(String tokenPassword) {
        this.tokenPassword = tokenPassword;
    }

    public String getSuitName() {
        return suitName;
    }

    public void setSuitName(String suitName) {
        this.suitName = suitName;
    }
}
