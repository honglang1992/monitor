package com.vrv.monitor.datapicker.controller.api;

import com.vrv.monitor.core.jvm.ClientStatus;
import com.vrv.monitor.core.jvm.StatusUtil;
import com.vrv.monitor.core.quartz.QuartzStatus;
import com.vrv.monitor.datapicker.service.api.RunStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Dendi on 2017/10/23.
 */

@RequestMapping("/api/runStatus")
@Controller
public class RunStatusController {

    private final static Logger logger = LoggerFactory.getLogger(RunStatusController.class);

    @Autowired
    private RunStatusService runStatusService;

    /**
     * 该进程的 jvm状态
     */
    @RequestMapping("/jvm")
    @ResponseBody
    public ClientStatus jvm(){
       ClientStatus status = StatusUtil.getClientStatus();
       return status;
    }

    /**
     * 该进程的任务调度 状态
     */
    @RequestMapping("/quartz")
    @ResponseBody
    public QuartzStatus schedule(){
        QuartzStatus status = new QuartzStatus();
        try {
            status = runStatusService.getQuartzStatus();
        }  catch (Exception e) {
            logger.error("/api/runStatus/quartz 获取数据异常",e);
        }
        return status;
    }
}
