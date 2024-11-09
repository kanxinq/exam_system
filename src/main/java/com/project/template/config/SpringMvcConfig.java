package com.project.template.config;

import com.project.template.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Resource
    LoginInterceptor loginInterceptor;
//定义一个私有变量，用于存储登录拦截器的实例
    public void addInterceptors(InterceptorRegistry registry) {
//        实现WebMvcConfigurer接口中的方法，用于注册自定义的拦截器
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
//                设置拦截器的匹配路径模式为所有路径

                //允许直接访问的接口
                .excludePathPatterns(
                        "/sysUser/login",
                        "/sysUser/register",
                        "/file/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/images/**",
                        "/webjars/**",
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/configuration/security",
                        "/doc.html"
                );
    }
}
