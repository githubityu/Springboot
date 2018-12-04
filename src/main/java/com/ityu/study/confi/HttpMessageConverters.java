package com.ityu.study.confi;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.ityu.study.fastjson.SimplePropertyFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * http 消息序列化配置
 *
 * @author lihe
 */
@Configuration
public class HttpMessageConverters {


    @Bean
    public org.springframework.boot.autoconfigure.http.HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setSerializeFilters(new SimplePropertyFilter());
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat, SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullBooleanAsFalse);
        fastConverter.setFastJsonConfig(fastJsonConfig);

        return new org.springframework.boot.autoconfigure.http.HttpMessageConverters((HttpMessageConverter<?>) fastConverter);
    }


}
