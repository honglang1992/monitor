package com.vrv.monitor.datapicker.dao.assetConfig;

import com.vrv.monitor.datapicker.model.assetConfig.MonitorHttpConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dendi on 2017/11/28.
 */
@Repository
public interface MonitorHttpConfigDao {

    /**
     * 获取 利用Http接口监控 的 设备的配置
     * @param typeUnicode
     * @param networkClass
     * @param areaCode
     * @return
     */
    List<MonitorHttpConfig> query(@Param("typeUnicode") String typeUnicode, @Param("networkClass")String networkClass, @Param("areaCode")String areaCode);
}
