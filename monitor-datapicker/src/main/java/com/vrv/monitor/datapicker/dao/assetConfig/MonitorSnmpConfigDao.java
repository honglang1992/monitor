package com.vrv.monitor.datapicker.dao.assetConfig;

import com.vrv.monitor.datapicker.model.assetConfig.MonitorSnmpConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Dendi on 2017/10/24.
 */
public interface MonitorSnmpConfigDao {

    List<MonitorSnmpConfig> query(@Param("networkClass") String networkClass, @Param("areaCode")String areaCode);
}
