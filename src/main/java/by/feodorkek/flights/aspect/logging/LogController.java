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

/**
 * A {@link Component} with a set of methods for helping logging calls and exceptions in methods
 * from aspect loggers
 * <p>
 * Designed to work with loggers that are based on aspects
 */
@Component
public class LogController {

    /**
     * Application config boolean value that allows logging all input information
     */
    @Value("${custom-config.log-aspects.enabled}")
    private boolean enabled;

    /**
     * Application config boolean value that allows logging all input information
     */
    @Value("${custom-config.log-aspects.print-exception-stacktrace}")
    private boolean printStacktrace;

    /**
     * Method for logging method calls from aspects
     *
     * @param logger    logger instance object
     * @param joinPoint reflective data of the called method
     */
    public void logCall(Logger logger, JoinPoint joinPoint) {
        if (enabled) {
            String prefix = buildPrefix(joinPoint);
            logger.info("{} - CALL", prefix);
            commonLog(logger, prefix, joinPoint);
        }
    }

    /**
     * Method for logging exceptions in methods calls from aspects
     *
     * @param logger    logger instance object
     * @param joinPoint reflective data of the called method
     * @param exception exception that occurred when executing the method
     */
    public void logException(Logger logger, JoinPoint joinPoint, Exception exception) {
        if (enabled) {
            String prefix = buildPrefix(joinPoint);
            logger.error("{} - EXCEPTION: {}", prefix, exception.getClass().getName());
            logger.error("{} - Message: {}", prefix, exception.getMessage());
            commonLog(logger, prefix, joinPoint);
            if (printStacktrace) {
                logger.error(prefix + " - Stacktrace:", exception);
            }
        }
    }

    /**
     * Common private method print {@code logger.info} about calls and exceptions in methods
     * <p>
     * Also displays some {@link Authentication} information from {@link SecurityContextHolder} context
     * and method input arguments
     *
     * @param logger    logger instance object
     * @param prefix    prefix for message in console
     * @param joinPoint reflective data of the called method
     */
    private void commonLog(Logger logger, String prefix, JoinPoint joinPoint) {
        // Authentication user data from SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("{} - Authentication: {}", prefix, authentication);
        logger.info("{} - Args: {}", prefix, joinPoint.getArgs());
    }

    /**
     * Build the prefix for log message based on {@link JoinPoint} reflective data of called method.
     * Example of returned formatted prefix:
     * <pre>
     *     [TestClassInstance] public void testMethod(String, UUID)
     * </pre>
     *
     * @param joinPoint reflective data of the called method
     * @return the prefix for log message
     */
    private String buildPrefix(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // Get class where method located
        String declaring = signature.getDeclaringType().getSimpleName();
        Method method = signature.getMethod();
        // Getting method modifiers (private, public, protected...)
        String modifiers = Modifier.toString(method.getModifiers());
        String methodName = method.getName();
        String returnType = method.getReturnType().getSimpleName();
        // Build incoming method parameters in one string by joining
        String parameters = Arrays.stream(method.getParameterTypes())
                .map(Class::getSimpleName)
                .collect(Collectors.joining(", "));
        // Return formatted method info string
        return String.format("[%s] %s %s %s(%s)", declaring, modifiers, returnType, methodName, parameters);
    }

}