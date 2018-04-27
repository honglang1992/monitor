package com.vrv.monitor.core.zabbix.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZabbixFieldDescription {
    /**
     * 描述
     * @return
     */
    String desc() default "";

    /**
     * 用于跟其它对象取值的 key
     * @return
     */
    String key() default "";

    /**
     * 用于跟其它对象取值的 key
     * @return
     */
    String triggerKey() default "";
}
