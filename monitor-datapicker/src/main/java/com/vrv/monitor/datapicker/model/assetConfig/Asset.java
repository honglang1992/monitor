package com.vrv.monitor.datapicker.model.assetConfig;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资产 entity
 */
public class Asset {
	private String guid;
	private String assetTypeSnoGuid;
	private String assetType;
	private String name;
	private String nameEn;
	private String ip;
	private String securityGuid; // 安全域guid
	private Long ipNum;
	private String versionInfo;
	private Date createTime;
	private String tags;

	private String mac;
	private String employeeCode1;
	private String employeeCode2;
	private String monitor;
	private String special;
	private String canMonitor;
	private String canRCtrl;
	private String assetNum; // 资产编号
	private String assetUse;// 资产用途
	private String location;// 物理位置
	private String describe;// 备注
	private String modelingjson;// 建模信息
	private String areaCode;// 地区
	private String  networkClass;//所属网络

	private BigDecimal lng;// 经度
	private BigDecimal lat;// 纬度
	
	/**
	 * 所属机房machineroomId
	 */
	private String machineroomId;
	
	/**
	 * 所属机柜guid
	 */
	private String cabinetGuid;

	/**
	 * 距离底部高度
	 */
	private int marginBottom;

	/**
	 * 占U口个数
	 */
	private int height;

	private String protocol;

	private String worth;
	private String secrecy;
	private String integrity;
	private String availability;
	private String typeUnicode;
	private String snoUnicode;
	// 当前资产的规则数量
	private int assetRuleNum;
}