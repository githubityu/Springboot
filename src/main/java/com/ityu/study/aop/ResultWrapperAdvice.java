package com.ityu.study.aop;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import com.ityu.study.annotation.ResponseBodyWrapper;
import com.ityu.study.util.R;
import com.ityu.study.util.RUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 用于进行统一的结果封装，该增强方法只针对存在 {@code ResponseBodyWrapper} 注解的Rest控制器生效
 *
 * @author lihe
 */
@RestControllerAdvice(annotations = ResponseBodyWrapper.class)
@Slf4j
public class ResultWrapperAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return FastJsonHttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        response.getHeaders().add("content-type", "application/json");
        if (body instanceof R) {
            return body;
        } else {
            return RUtil.ok(body);
        }

    }
}
