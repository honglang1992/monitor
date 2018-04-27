package com.vrv.monitor.datapicker.dao.assetConfig;

import com.vrv.monitor.datapicker.model.assetConfig.MonitorZabbixConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dendi on 2017/10/18.
 */
@Repository
public interface MonitorZabbixConfigDao {
    List<MonitorZabbixConfig> query(@Param("networkClass") String networkClass, @Param("areaCode")String areaCode);
}
