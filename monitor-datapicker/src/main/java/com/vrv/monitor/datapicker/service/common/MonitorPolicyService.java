package com.vrv.monitor.datapicker.service.common;

import com.vrv.monitor.datapicker.model.common.MonitorPolicy;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * 监控策略服务接口
 * Created by Dendi on 2017/10/17.
 */
public interface MonitorPolicyService {

    /**
     * 初始化监控策略
     * @param policies
     */
    void init (List<MonitorPolicy> policies);

    /**
     * 更新监控策略 merge操作
     * @param policies
     */
    void merge(List<MonitorPolicy> policies);

    /**
     * 删除所有监控策略
     */
    void deleteAll();

    /**
     * 查询当前已加载的监控策略
     * @return
     */
    List<MonitorPolicy> query() throws SchedulerException;
}
