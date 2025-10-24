package com.invest.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.invest.utils.ApiLoggingFilter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<ApiLoggingFilter> loggingFilter(ApiLoggingFilter filter) {
        FilterRegistrationBean<ApiLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/*"); // aplicar para todos os endpoints
        registrationBean.setOrder(1); // prioridade do filter
        return registrationBean;
    }
}