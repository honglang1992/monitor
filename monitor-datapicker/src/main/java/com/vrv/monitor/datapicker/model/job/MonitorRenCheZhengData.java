package com.vrv.monitor.datapicker.model.job;

/**
 * 人车证系统 监控信息
 * Created by Dendi on 2017/11/26.
 */
public class MonitorRenCheZhengData {
    // 设备guid
    private String assetGuid;

    //是否能连接到设备
    private Boolean icmpPing = true;

    //车牌识别器
    private Integer licensePlateRecognizer;

    //道闸
    private Integer barrier;

    //人证摄像头
    private Integer personnelCamera;

    //身份证阅读器
    private Integer IdCardReader;

    //一体式门禁
    private Integer accessController;


    public String getAssetGuid() {
        return assetGuid;
    }

    public void setAssetGuid(String assetGuid) {
        this.assetGuid = assetGuid;
    }

    public Integer getLicensePlateRecognizer() {
        return licensePlateRecognizer;
    }

    public void setLicensePlateRecognizer(Integer licensePlateRecognizer) {
        this.licensePlateRecognizer = licensePlateRecognizer;
    }

    public Integer getBarrier() {
        return barrier;
    }

    public void setBarrier(Integer barrier) {
        this.barrier = barrier;
    }

    public Integer getPersonnelCamera() {
        return personnelCamera;
    }

    public void setPersonnelCamera(Integer personnelCamera) {
        this.personnelCamera = personnelCamera;
    }

    public Integer getIdCardReader() {
        return IdCardReader;
    }

    public void setIdCardReader(Integer idCardReader) {
        IdCardReader = idCardReader;
    }

    public Integer getAccessController() {
        return accessController;
    }

    public void setAccessController(Integer accessController) {
        this.accessController = accessController;
    }

    public Boolean getIcmpPing() {
        return icmpPing;
    }

    public void setIcmpPing(Boolean icmpPing) {
        this.icmpPing = icmpPing;
    }
}
