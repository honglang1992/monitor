package com.vrv.monitor.datapicker.model.assetConfig;

/**
 * Created by Dendi on 2017/10/24.
 */
public class MonitorSnmpConfig {

    /**
     * 设备，除了snmp外 额外监控项
     */
    private String assetExtraInfo;

    private String ip;

    private String assetGuid;
    private String snmpVersion;
    private int port;
    private String readcommunityString;
    private String securityLevel;
    private String securityName;
    private String privPassWord;
    private String authPassWord;
    private String authAlgorithm;
    private String privAlgorithm;
    /**
     * 是否开启监控；0：停止，1：启动
     */
    private Integer status;

    public String getAssetExtraInfo() {
        return assetExtraInfo;
    }

    public void setAssetExtraInfo(String assetExtraInfo) {
        this.assetExtraInfo = assetExtraInfo;
    }

    public String getAssetGuid() {
        return assetGuid;
    }

    public void setAssetGuid(String assetGuid) {
        this.assetGuid = assetGuid;
    }

    public String getSnmpVersion() {
        return snmpVersion;
    }

    public void setSnmpVersion(String snmpVersion) {
        this.snmpVersion = snmpVersion;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getReadcommunityString() {
        return readcommunityString;
    }

    public void setReadcommunityString(String readcommunityString) {
        this.readcommunityString = readcommunityString;
    }

    public String getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public String getPrivPassWord() {
        return privPassWord;
    }

    public void setPrivPassWord(String privPassWord) {
        this.privPassWord = privPassWord;
    }

    public String getAuthPassWord() {
        return authPassWord;
    }

    public void setAuthPassWord(String authPassWord) {
        this.authPassWord = authPassWord;
    }

    public String getAuthAlgorithm() {
        return authAlgorithm;
    }

    public void setAuthAlgorithm(String authAlgorithm) {
        this.authAlgorithm = authAlgorithm;
    }

    public String getPrivAlgorithm() {
        return privAlgorithm;
    }

    public void setPrivAlgorithm(String privAlgorithm) {
        this.privAlgorithm = privAlgorithm;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
