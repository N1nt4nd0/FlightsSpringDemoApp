package by.feodorkek.flights.aspect.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class LogController {

    @Value("${custom-config.log-aspects.enabled}")
    private boolean enabled;

    public void logCall(Logger logger, JoinPoint joinPoint) {
        if (enabled) {
            String prefix = buildPrefix(joinPoint);
            logger.info("{} - [Called]", prefix);
            commonLog(logger, prefix, joinPoint);
        }
    }

    public void logException(Logger logger, JoinPoint joinPoint, Exception exception) {
        if (enabled) {
            String prefix = buildPrefix(joinPoint);
            logger.error("{} - [Exception]", prefix);
            logger.error("{} - [Message]: {}", prefix, exception.getMessage());
            commonLog(logger, prefix, joinPoint);
        }
    }

    private void commonLog(Logger logger, String prefix, JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("{} - [Authentication]: {}", prefix, authentication);
        logger.info("{} - [Args]: {}", prefix, joinPoint.getArgs());
    }

    private String buildPrefix(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String declaring = signature.getDeclaringType().getSimpleName();
        String modifiers = Modifier.toString(method.getModifiers());
        String methodName = method.getName();
        String returnType = method.getReturnType().getSimpleName();
        String parameters = Arrays.stream(method.getParameterTypes()).map(Class::getSimpleName).collect(Collectors.joining(", "));
        return String.format("[%s] %s %s %s(%s)", declaring, modifiers, returnType, methodName, parameters);
    }

}