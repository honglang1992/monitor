package com.vrv.monitor.datapicker.model.assetConfig;

public class MonitorZabbixConfig  {
	 private String id;
     private String assetGuid;
     private String serverUrl;
     private String username;
     private String pwd;
     private Integer hostId;
     private Integer status;

     public String getId() {
          return id;
     }

     public void setId(String id) {
          this.id = id;
     }

     public String getAssetGuid() {
          return assetGuid;
     }

     public void setAssetGuid(String assetGuid) {
          this.assetGuid = assetGuid;
     }

     public String getServerUrl() {
          return serverUrl;
     }

     public void setServerUrl(String serverUrl) {
          this.serverUrl = serverUrl;
     }

     public String getUsername() {
          return username;
     }

     public void setUsername(String username) {
          this.username = username;
     }

     public String getPwd() {
          return pwd;
     }

     public void setPwd(String pwd) {
          this.pwd = pwd;
     }

     public Integer getHostId() {
          return hostId;
     }

     public void setHostId(Integer hostId) {
          this.hostId = hostId;
     }

     public Integer getStatus() {
          return status;
     }

     public void setStatus(Integer status) {
          this.status = status;
     }
}