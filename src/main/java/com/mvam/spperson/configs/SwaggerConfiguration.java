package com.mvam.spperson.configs;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class SwaggerConfiguration {
    private final String title;
    private final String description;
    private final String termsOfServiceUrl;
    private final String license;
    private final String licenseUrl;
    private final String version;
    private final Optional<String> server;

    public SwaggerConfiguration(
            @Value("${spring.application.swagger.title}")
                    String title,
            @Value("${spring.application.swagger.description}")
                    String description,
            @Value("${spring.application.swagger.terms-of-service-url}")
                    String termsOfServiceUrl,
            @Value("${spring.application.swagger.license}")
                    String license,
            @Value("${spring.application.swagger.license-url}")
                    String licenseUrl,
            @Value("${spring.application.version}")
                    String version,
            @Value("${springdoc.server:#{null}}")
                    Optional<String> server
    ) {
        this.title = title;
        this.description = description;
        this.termsOfServiceUrl = termsOfServiceUrl;
        this.license = license;
        this.licenseUrl = licenseUrl;
        this.version = version;
        this.server = server;
    }

    @Bean
    public OpenAPI getOpenAPI() {
        OpenAPI api = new OpenAPI()
                .components(new Components())
                .info(
                        new Info()
                                .title(title)
                                .description(description)
                                .termsOfService(termsOfServiceUrl)
                                .version(version)
                                .license(
                                        new License()
                                                .name(license)
                                                .url(licenseUrl)
                                )
                );
        if (server.isPresent()) {
            api.addServersItem(
                    new Server()
                            .url(server.get())
            );
        }
        return api;
    }
}
