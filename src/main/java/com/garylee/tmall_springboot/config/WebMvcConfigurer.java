package com.garylee.tmall_springboot.config;

import com.garylee.tmall_springboot.Interceptor.LoginInterceptor;
import com.garylee.tmall_springboot.Interceptor.OtherInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by GaryLee on 2018-12-02 23:57.
 */
@Configuration
class WebMvcConfigurer extends WebMvcConfigurerAdapter{
    @Bean
    public LoginInterceptor getLoginIntercepter(){
        return new LoginInterceptor();
    }
    @Bean
    public OtherInterceptor getOtherInterceptor(){
        return new OtherInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoginIntercepter()).addPathPatterns("/**");
        registry.addInterceptor(getOtherInterceptor()).addPathPatterns("/**");
    }
}
