package com.qm.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 博客后台操作日志器
 *
 * @Author: qin
 * @Date: 2023/6/7 14:55
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BackgroundOperationLogger {

    /**
     * 业务名称
     */
    String value() default "";

    /**
     * 是否将当前日志记录到数据库中
     */
    boolean save() default true;
}
