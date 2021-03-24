package com.ruiheng.eduservice.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

@Configuration
@MapperScan("com.ruiheng.eduservice.mapper")
public class EduConfig {
	 @Bean
	    public ISqlInjector sqlInjector() {
	        return new LogicSqlInjector();
	    }

	    /**
	     * 分页插件……
	     */
	    @Bean
	    public PaginationInterceptor paginationInterceptor() {
	        return new PaginationInterceptor();
	    }
}
