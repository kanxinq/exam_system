package com.project.template.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//手动导入包 alt+enter

/**
 * 阻止恶意的全表更新删除
 */
@Configuration
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
//        定义一个方法，返回一个MybatisPlusInterceptor对象，用于配置 MyBatis-Plus 的拦截器
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        创建一个MybatisPlusInterceptor对象
        //正确的
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
//        向拦截器中添加一个内部拦截器，这里是一个用于 MySQL 数据库的分页内部拦截器
        return interceptor;
//        返回配置好的MybatisPlusInterceptor对象
    }

}
