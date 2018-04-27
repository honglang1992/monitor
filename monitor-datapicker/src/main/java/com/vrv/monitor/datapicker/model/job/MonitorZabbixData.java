package com.vrv.monitor.datapicker.model.job;


import com.vrv.monitor.core.util.UUIDUtil;
import com.vrv.monitor.core.zabbix.annotation.ZabbixFieldDescription;

/**
 * zabbix监控的数据
 *
 * @author Dendi 20170712
 *
 */
public class MonitorZabbixData {

	private String guid = UUIDUtil.getUUID();

	@ZabbixFieldDescription(desc = "设备guid")
	private String assetGuid;

	@ZabbixFieldDescription(desc = "系统版本", key = "infoportal.version[3dQtGui]")
	private String dQtGuiVersion;

	@ZabbixFieldDescription(desc = "磁盘空闲率", key = "vfs.fs.size[/,pfree]")
	private Float diskFreePercent;

	@ZabbixFieldDescription(desc = "CPU空闲率", key = "system.cpu.util[,idle]")
	private Float cpuIdlePercent;

	@ZabbixFieldDescription(desc = "内存空闲率", key = "vm.memory.free[percent]")
	private Float memoryFreePercent;

	@ZabbixFieldDescription(desc = "I/O使用率", key = "system.cpu.util[,iowait]")
	private Float ioUsePercent;

	@ZabbixFieldDescription(desc = "网络可用性", key = "icmpping")
	private Boolean icmpPing;

	@ZabbixFieldDescription(desc = "网络带宽", key = "infoportal.network_bandwidth")
	private Float netWorkBandWidth;

	@ZabbixFieldDescription(desc = "Primesense相机", key = "infoportal.hardware_status[primesense60]")
	private Float primesenseCamera;

	@ZabbixFieldDescription(desc = "二维相机", key = "infoportal.hardware_status[2dcamera10]")
	private Float camera2D;

	@ZabbixFieldDescription(desc = "闸机", key = "infoportal.hardware_status[gate]")
	private Boolean gate;

	@ZabbixFieldDescription(desc = "身份证读卡器", key = "infoportal.hardware_status[idcard]")
	private Boolean idCard;

	@ZabbixFieldDescription(desc = "nvidia显卡", key = "infoportal.hardware_status[nvidia]")
	private Boolean nvidia;

	@ZabbixFieldDescription(desc = "声卡", key = "infoportal.hardware_status[soundcard]")
	private Boolean soundcard;

	@ZabbixFieldDescription(desc = "MysqlServer", key = "mysql.ping")
	private Boolean mysql;

	@ZabbixFieldDescription(desc = "Raid disk", key = "infoportal.hardware_status[raid]")
	private Boolean raidDisk;

	@ZabbixFieldDescription(desc = "Gate manager", key = "infoportal.app_status[gatemanager]")
	private Boolean gateManager;

	@ZabbixFieldDescription(desc = "3dQtGui", key = "infoportal.app_status[3dQtGui]")
	private Boolean dQtGui;

	@ZabbixFieldDescription(desc = "Upload", key = "infoportal.app_status[upload]")
	private Boolean upload;

	@ZabbixFieldDescription(desc = "wifiServer", key = "infoportal.app_status[wifiserver]")
	private Integer wifiServer;

	@ZabbixFieldDescription(desc = "手机侦码采集进程", key = "infoportal.app_status[cellphone]")
	private Float cellphoneCollect;

	@ZabbixFieldDescription(desc = "平均通行时间", key = "infoportal.key_stat[personpassavg]")
	private Float personPassAvg;

	@ZabbixFieldDescription(desc = "最大通行时间", key = "infoportal.key_stat[personpassmax]")
	private Float personPassMax;

	@ZabbixFieldDescription(desc = "人证比对成功率", key = "infoportal.key_stat[1v1okratio]")
	private Float okRatio;

	@ZabbixFieldDescription(desc = "最优帧选取成功率", key = "infoportal.key_stat[2dbestframeratio]")
	private Float bestFrameRatio;

	@ZabbixFieldDescription(desc = "三维建模", key = "infoportal.key_stat[3dmodelratio]")
	private Float modelRatio3d;

	@ZabbixFieldDescription(desc = "布控服务")
	private Boolean webBuKong;

	@ZabbixFieldDescription(desc = "采集上传中心端服务")
	private Boolean webCollectUpload;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getAssetGuid() {
		return assetGuid;
	}

	public void setAssetGuid(String assetGuid) {
		this.assetGuid = assetGuid;
	}

	public String getdQtGuiVersion() {
		return dQtGuiVersion;
	}

	public void setdQtGuiVersion(String dQtGuiVersion) {
		this.dQtGuiVersion = dQtGuiVersion;
	}

	public Float getDiskFreePercent() {
		return diskFreePercent;
	}

	public void setDiskFreePercent(Float diskFreePercent) {
		this.diskFreePercent = diskFreePercent;
	}

	public Float getCpuIdlePercent() {
		return cpuIdlePercent;
	}

	public void setCpuIdlePercent(Float cpuIdlePercent) {
		this.cpuIdlePercent = cpuIdlePercent;
	}

	public Float getMemoryFreePercent() {
		return memoryFreePercent;
	}

	public void setMemoryFreePercent(Float memoryFreePercent) {
		this.memoryFreePercent = memoryFreePercent;
	}

	public Float getIoUsePercent() {
		return ioUsePercent;
	}

	public void setIoUsePercent(Float ioUsePercent) {
		this.ioUsePercent = ioUsePercent;
	}

	public Boolean getIcmpPing() {
		return icmpPing;
	}

	public void setIcmpPing(Boolean icmpPing) {
		this.icmpPing = icmpPing;
	}

	public Float getNetWorkBandWidth() {
		return netWorkBandWidth;
	}

	public void setNetWorkBandWidth(Float netWorkBandWidth) {
		this.netWorkBandWidth = netWorkBandWidth;
	}

	public Float getPrimesenseCamera() {
		return primesenseCamera;
	}

	public void setPrimesenseCamera(Float primesenseCamera) {
		this.primesenseCamera = primesenseCamera;
	}

	public Float getCamera2D() {
		return camera2D;
	}

	public void setCamera2D(Float camera2D) {
		this.camera2D = camera2D;
	}

	public Boolean getGate() {
		return gate;
	}

	public void setGate(Boolean gate) {
		this.gate = gate;
	}

	public Boolean getIdCard() {
		return idCard;
	}

	public void setIdCard(Boolean idCard) {
		this.idCard = idCard;
	}

	public Boolean getNvidia() {
		return nvidia;
	}

	public void setNvidia(Boolean nvidia) {
		this.nvidia = nvidia;
	}

	public Boolean getSoundcard() {
		return soundcard;
	}

	public void setSoundcard(Boolean soundcard) {
		this.soundcard = soundcard;
	}

	public Boolean getMysql() {
		return mysql;
	}

	public void setMysql(Boolean mysql) {
		this.mysql = mysql;
	}

	public Boolean getRaidDisk() {
		return raidDisk;
	}

	public void setRaidDisk(Boolean raidDisk) {
		this.raidDisk = raidDisk;
	}

	public Boolean getGateManager() {
		return gateManager;
	}

	public void setGateManager(Boolean gateManager) {
		this.gateManager = gateManager;
	}

	public Boolean getdQtGui() {
		return dQtGui;
	}

	public void setdQtGui(Boolean dQtGui) {
		this.dQtGui = dQtGui;
	}

	public Boolean getUpload() {
		return upload;
	}

	public void setUpload(Boolean upload) {
		this.upload = upload;
	}

	public Integer getWifiServer() {
		return wifiServer;
	}

	public void setWifiServer(Integer wifiServer) {
		this.wifiServer = wifiServer;
	}

	public Float getCellphoneCollect() {
		return cellphoneCollect;
	}

	public void setCellphoneCollect(Float cellphoneCollect) {
		this.cellphoneCollect = cellphoneCollect;
	}

	public Float getPersonPassAvg() {
		return personPassAvg;
	}

	public void setPersonPassAvg(Float personPassAvg) {
		this.personPassAvg = personPassAvg;
	}

	public Float getPersonPassMax() {
		return personPassMax;
	}

	public void setPersonPassMax(Float personPassMax) {
		this.personPassMax = personPassMax;
	}

	public Float getOkRatio() {
		return okRatio;
	}

	public void setOkRatio(Float okRatio) {
		this.okRatio = okRatio;
	}

	public Float getBestFrameRatio() {
		return bestFrameRatio;
	}

	public void setBestFrameRatio(Float bestFrameRatio) {
		this.bestFrameRatio = bestFrameRatio;
	}

	public Float getModelRatio3d() {
		return modelRatio3d;
	}

	public void setModelRatio3d(Float modelRatio3d) {
		this.modelRatio3d = modelRatio3d;
	}

	public Boolean getWebBuKong() {
		return webBuKong;
	}

	public void setWebBuKong(Boolean webBuKong) {
		this.webBuKong = webBuKong;
	}

	public Boolean getWebCollectUpload() {
		return webCollectUpload;
	}

	public void setWebCollectUpload(Boolean webCollectUpload) {
		this.webCollectUpload = webCollectUpload;
	}
}
