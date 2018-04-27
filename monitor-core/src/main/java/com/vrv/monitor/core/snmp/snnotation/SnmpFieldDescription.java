package com.vrv.monitor.core.snmp.snnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Dendi on 2017/11/14.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SnmpFieldDescription {

    /**
     * oid
     * @return
     */
    String oid() default "";

    /**
     * oid对应的信息
     * @return
     */
    String desc() default "";
}
