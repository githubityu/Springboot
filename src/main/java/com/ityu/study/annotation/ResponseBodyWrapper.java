package com.ityu.study.annotation;


import java.lang.annotation.*;

/**
 * 响应值包装注解,该注解被{@link “ResultWrapperAdvice”}解析
 *
 * @author lihe
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseBodyWrapper {


}
