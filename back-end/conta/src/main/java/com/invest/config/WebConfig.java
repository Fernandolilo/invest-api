package com.invest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.invest.utils.ApiLoggingFilter;



@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private ApiLoggingFilter apiLoggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(apiLoggingInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/actuator/**", "/swagger-ui/**");

    }
}
