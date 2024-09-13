package by.feodorkek.flights.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class SwaggerConfig {

    @Value("${api.name}")
    private String apiName;

    @Value("${api.version}")
    private String apiVersion;

    @Value("${api.path}")
    private String apiPath;

    @Bean
    public OpenAPI securedOpenApi() {
        OpenAPI openApi = new OpenAPI();
        Info openApiInfo = new Info().title(apiName).version(apiVersion);
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Bearer Authentication");
        SecurityScheme securityScheme = new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT");
        Components secureComponents = new Components().addSecuritySchemes("Bearer Authentication", securityScheme);
        return openApi.addSecurityItem(securityRequirement).components(secureComponents).info(openApiInfo);
    }

}