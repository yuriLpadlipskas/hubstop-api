package br.com.hubspot.integration.api.config;

import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            Server server = new Server();
            if ("dev".equalsIgnoreCase(activeProfile)) {
                server.setUrl("http://localhost:9091/hubspot");
            } else {
                server.setUrl("https://hubspot-api-production.up.railway.app/hubspot");
            }
            openApi.setServers(List.of(server));
        };
    }
}

