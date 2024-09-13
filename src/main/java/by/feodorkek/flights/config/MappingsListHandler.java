package by.feodorkek.flights.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Slf4j
@Component
@RequiredArgsConstructor
public class MappingsListHandler {

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Value("${custom-config.log-mappings}")
    private boolean logMappings;

    @PostConstruct
    public void printMappings() {
        if (logMappings) {
            requestMappingHandlerMapping.getHandlerMethods().forEach((key, value) -> {
                log.info(key + " : " + value);
            });
        }
    }

}