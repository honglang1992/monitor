package com.vrv.monitor.datapicker.dao.assetConfig;

import com.vrv.monitor.datapicker.model.assetConfig.MonitorSuite;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dendi on 2017/10/11.
 */
@Repository
public interface MonitorSuiteDao {
    MonitorSuite getMonitorSuite(int id);

    @Select("select * from monitor_suite where monitor_freq > 0 and methodName = ''")
    List<MonitorSuite> getItemsOnMonitor();

    @Select("select monitor_freq from monitor_suite where methodName = '' and  suiteName = #{suiteName} limit 1")
    String getMonitorPeriodBySuitName(@Param("suiteName") String suiteName);
}
