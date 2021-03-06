package com.ityu.study.confi;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 * 在与spring boot集成时，放在与Application.java同级的目录下。
 * 通过@Configuration注解，让Spring来加载该类配置。
 * 再通过@EnableSwagger2注解来启用Swagger2。
 */
@Configuration
@EnableSwagger2
public class Swagger2  extends BaseSwaggerConfig{

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://项目实际地址/swagger-ui.html
     *
     * @return
     */
    @Override
    protected ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Ma-vendor-api")
                .description("Ma-vendor-api")
                .termsOfServiceUrl("http://www.build.com")
                .version("1.0.0")
                .build();
    }
}

