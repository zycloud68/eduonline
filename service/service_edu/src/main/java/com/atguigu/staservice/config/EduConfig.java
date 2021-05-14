package com.atguigu.staservice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 配置类,必须拥有
 */
@Configuration
@EnableSwagger2
@MapperScan("com.atguigu.staservice.mapper")
public class EduConfig {
    // 逻辑删除类
    @Bean
    public ISqlInjector sqlInjector(){
        return new LogicSqlInjector();
    }
    // 分页查询的逻辑配置
    @Bean
    public PaginationInterceptor paginationInterceptor(){

        return new PaginationInterceptor();
    }

}
