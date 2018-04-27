package com.vrv.monitor.core.snmp;

import java.util.List;

/**
 * Created by Dendi on 2017/11/15.
 */
public interface SnmpTargetService {
    Object get(String snmpTarget);

    List getList(String snmpTarget);
}
