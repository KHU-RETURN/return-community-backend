package com.khureturn.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;


@Configuration
public class ConfigureImp implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:8080")
                .allowedHeaders("authorization", "User-Agent", "Cache-Control", "Content-Type")
                .exposedHeaders("authorization", "User-Agent", "Cache-Control", "Content-Type")
                .allowedMethods("*")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/profileImg/**").addResourceLocations("/WEB-INF/static/profileImg/")
                .setCacheControl(CacheControl.maxAge(2, TimeUnit.MINUTES).cachePublic());
    }



}

