package com.vrv.monitor.datapicker.model.snmp;

import com.vrv.monitor.core.snmp.snnotation.SnmpClassDescription;
import com.vrv.monitor.core.snmp.snnotation.SnmpFieldDescription;
import com.vrv.monitor.datapicker.common.SysConstants;

/**
 * Created by Dendi on 2017/11/14.
 */
@SnmpClassDescription(type=2,key=SysConstants.SNMP_PROCESSINFO)
public class ProcessInfo {

    @SnmpFieldDescription(desc="进程id",oid="1.3.6.1.2.1.25.4.2.1.1")
    public int processId;

    @SnmpFieldDescription(desc="进程名称",oid="1.3.6.1.2.1.25.4.2.1.2")
    public String processName;

    @SnmpFieldDescription(desc="启动路径",oid="1.3.6.1.2.1.25.4.2.1.4")
    public String processStartPath;

    @SnmpFieldDescription(desc="启动参数",oid="1.3.6.1.2.1.25.4.2.1.5")
    public String processStartParams;

    @SnmpFieldDescription(desc="进程类型",oid="1.3.6.1.2.1.25.4.2.1.6")
    public int processType;

    @SnmpFieldDescription(desc="运行状态",oid="1.3.6.1.2.1.25.4.2.1.7")
    public String processRunStatus;

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessStartPath() {
        return processStartPath;
    }

    public void setProcessStartPath(String processStartPath) {
        this.processStartPath = processStartPath;
    }

    public String getProcessStartParams() {
        return processStartParams;
    }

    public void setProcessStartParams(String processStartParams) {
        this.processStartParams = processStartParams;
    }

    public String getProcessRunStatus() {
        return processRunStatus;
    }

    public void setProcessRunStatus(String processRunStatus) {
        this.processRunStatus = processRunStatus;
    }

    public int getProcessType() {
        return processType;
    }

    public void setProcessType(int processType) {
        this.processType = processType;
    }
}
