package com.khureturn.community.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        Server server = new Server();
        server.setUrl("https://khu-return.site");
        List<Server> servers = new ArrayList<>();
        servers.add(server);
        return new OpenAPI()
                .components(new Components()).servers(servers).info(apiInfo());

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
