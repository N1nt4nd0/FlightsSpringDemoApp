package by.feodorkek.flights.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * {@link Component @Component} that is used for output to log info about {@link RequestMappingInfo} instances from type-level
 * and method-level {@link RequestMapping} and {@link HttpExchange} annotations in {@link Controller} classes.
 * <p>
 * Will be called once, automatically after the Spring bean has completed initialization
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MappingsListHandler {

    /**
     * {@link RequestMappingHandlerMapping} instance from Spring context
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor}
     */
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    /**
     * Application config boolean value which allows output log info
     */
    @Value("${custom-config.log-mappings}")
    private boolean logMappings;

    /**
     * Main method which calls after the Spring bean has completed initialization.
     * Outputs info about registered mappings in log with {@code INFO} level
     * <p>
     * Used {@link PostConstruct @PostConstruct} annotation and called once automatically
     * after the Spring bean has completed initialization
     */
    @PostConstruct
    public void printMappings() {
        if (logMappings) {
            requestMappingHandlerMapping.getHandlerMethods().forEach((key, value) -> {
                log.info(key + " : " + value);
            });
        }
    }

}