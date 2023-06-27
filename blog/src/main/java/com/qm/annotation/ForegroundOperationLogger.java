package com.qm.annotation;

import java.lang.annotation.*;

/**
 * 博客前台操作日志器
 *
 * @Author: qin
 * @Date: 2023/6/7 14:35
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ForegroundOperationLogger {
    String value() default ""; // 操作模块

    String type() default "";  // 操作类型

    String desc() default "";  // 操作描述
    boolean save() default true; //是否将当前日志记录到数据库中
}
