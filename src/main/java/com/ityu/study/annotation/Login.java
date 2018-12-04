package com.ityu.study.annotation;

import java.lang.annotation.*;

/**
 * 权限注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {


    /**
     * 是否需要为居民用户权限
     */
    boolean resident() default false;

}
