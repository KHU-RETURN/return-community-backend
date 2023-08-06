package com.khureturn.community.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        String title = "RETURN COMMUNITY";
        String description = "RETURN COMMUNITY API Documentation";
        return new Info()
                .title(title)
                .description(description)
                .version("1.0");
    }
}
