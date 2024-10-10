package by.feodorkek.flights.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Main application security configuration. Here is important annotations:
 * <p>
 * {@link EnableWebSecurity @EnableWebSecurity} - enabling SpringSecurity features defined in any
 * {@link WebSecurityConfigurer} or more likely by exposing a {@link SecurityFilterChain} bean
 * <p>
 * {@link EnableMethodSecurity @EnableMethodSecurity} - enabling annotation for specifying a method access-control
 * expression which will be evaluated to decide whether a method invocation is allowed or not. For example annotation
 * {@code @PreAuthorize("hasRole('ADMIN')")} over the class controller will grant access to mappings for users with
 * role {@code ADMIN}
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    /**
     * {@link AuthenticationExceptionHandler} instance from Spring context
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor}
     * which handles when any exception
     * occurs during authentication.
     */
    private final AuthenticationExceptionHandler authenticationExceptionHandler;

    /**
     * {@link AuthenticationFilter} instance from Spring context
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor}
     * which handles user token authentication
     */
    private final AuthenticationFilter authenticationFilter;

    /**
     * Application config string value - API root path of all controllers endpoints
     */
    @Value("${api.path}")
    private String apiPath;

    /**
     * {@link Bean @Bean} which configures a chain of security filters for application
     *
     * @param http Spring Security's element in the namespace configuration.
     *             It allows configuring web based security for specific http requests
     * @return customized {@link SecurityFilterChain}
     * @throws Exception exception which can be thrown during {@link HttpSecurity} configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Application used by non-browser clients, that's why CSRF protection may be disabled
        http.csrf(AbstractHttpConfigurer::disable);
        // Configures authorization rules for different URLs
        http.authorizeHttpRequests((auth) -> {
            // Allows access to all URLs associated with Swagger documentation without authentication
            auth.requestMatchers("/swagger-ui/**", "/swagger-resources/*", "/v3/api-docs/**").permitAll();
            // Allows access to all authentication-related URLs without authentication
            auth.requestMatchers(apiPath + "/auth/**").permitAll();
            // Requires authentication for all other URLs starting with apiPath
            auth.requestMatchers(apiPath + "/**").authenticated();
        });
        // Setts an exception handler for authentication using authenticationExceptionHandler
        http.exceptionHandling(handler -> handler.authenticationEntryPoint(authenticationExceptionHandler));
        // Adds a custom authenticationFilter before the standard UsernamePasswordAuthenticationFilter
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * {@link Bean @Bean} which needed to provide a centralized and customizable way to authenticate
     * users to an application. It allows you to easily configure and use the {@link AuthenticationManager}
     * in different components such as controllers or services, providing consistency and simplifying
     * authentication management
     *
     * @param configuration input configuration from Spring context
     * @return authentication manager
     * @throws Exception exception which can be thrown in {@code getAuthenticationManager()} method
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Global Spring Security password encoder
     *
     * @return {@link PasswordEncoder} object that will be managed by the Spring container and
     * available to be injected into other beans
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder with parameter 12, which indicates the strength (or "logarithmic cost") of the hash
        return new BCryptPasswordEncoder(12);
    }

}