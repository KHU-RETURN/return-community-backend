package com.khureturn.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000", "http://localhost:8080")
                    .allowedOrigins("*")
                    .allowedHeaders("authorization", "authorization-refresh", "User-Agent", "Cache-Control", "Content-Type")
                    .exposedHeaders("authorization", "authorization-refresh", "User-Agent", "Cache-Control", "Content-Type")
                    .allowedMethods("*");
            //.allowCredentials(true);
        }

}
