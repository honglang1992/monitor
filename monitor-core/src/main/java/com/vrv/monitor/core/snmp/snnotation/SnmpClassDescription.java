package com.vrv.monitor.core.snmp.snnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Dendi on 2017/11/14.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SnmpClassDescription {
    /**
     * 采集方式 1.get 2.walk
     */
    int type() default 1 ;

    String key() default "" ;
}
