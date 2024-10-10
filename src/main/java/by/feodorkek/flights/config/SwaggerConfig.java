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

/**
 * Configuration for Swagger UI components
 */
@Getter
@Configuration
public class SwaggerConfig {

    /**
     * Application config value which provides the name of API
     */
    @Value("${api.name}")
    private String apiName;

    /**
     * API version from application config
     */
    @Value("${api.version}")
    private String apiVersion;

    /**
     * API root path of all controllers endpoints
     */
    @Value("${api.path}")
    private String apiPath;

    /**
     * {@link Bean @Bean} which configures Swagger UI components
     *
     * @return {@link OpenAPI} Swagger UI OpenApi configuration
     */
    @Bean
    public OpenAPI securedOpenApi() {
        return new OpenAPI()
                // Adding authorize component with JWT Token authentication and 'Authorize' button to main UI
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(
                        new Components().addSecuritySchemes("Bearer Authentication",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
                )
                // Adding apiName and apiVersion ti main UI
                .info(new Info().title(apiName).version(apiVersion));
    }

}