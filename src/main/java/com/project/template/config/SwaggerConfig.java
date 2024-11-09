package com.project.template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket() {
//        定义一个方法，返回一个Docket对象，用于配置 Swagger 的文档生成
        return new Docket(DocumentationType.SWAGGER_2)
//                创建一个新的Docket对象，并配置其属性
                .select()
//                调用select方法开始配置选择器
                .apis(RequestHandlerSelectors.basePackage("com.project.template.controller"))
//                使用请求处理器选择器构建器类，选择包名为com.project.template.controller下的所有控制器
                .paths(PathSelectors.any())
//                使用路径选择器构建器类，选择任何路径
                .build().apiInfo(new ApiInfoBuilder().title("SpringBoot+Vue前后端分离系统").version("v1.0").build());
//        调用build方法构建Docket对象，并设置 API 信息，包括标题为 “SpringBoot+Vue 前后端分离系统”，版本为 “v1.0”
    }

}
