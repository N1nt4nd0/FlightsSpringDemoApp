package by.feodorkek.flights.aspect.logging.loggers;

import by.feodorkek.flights.aspect.logging.LogController;
import by.feodorkek.flights.aspect.logging.ExcludeFromAspectLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The {@code ServicesLogAspect} is a aspect for logging calls
 * and exceptions in methods from services who are
 * located in {@code by.feodorkek.flights.service.impl} package
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ServicesLogAspect {

    /**
     * Pointcut expression which indicates the goals of the aspect:
     * methods of all classes in the {@code by.feodorkek.flights.service.impl} package
     * and ignore methods marked with the {@link ExcludeFromAspectLog @ExcludeFromAspectLog} annotation
     */
    private final String pointcutExpression = """
            execution(
                * by.feodorkek.flights.service.impl..*(..)) &&
                !@annotation(by.feodorkek.flights.aspect.logging.ExcludeFromAspectLog
            )
            """;

    /**
     * {@link LogController} instance from Spring context
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor}
     */
    private final LogController logController;

    /**
     * Application config value that allows to display information about a method call,
     * formatted in {@link LogController} to be output to the console
     */
    @Value("${custom-config.log-aspects.log-services.calls}")
    private boolean logCalls;

    /**
     * Application config value that allows to display information about
     * exceptions that occurred when calling a method,
     * formatted in {@link LogController} to be output to the console
     */
    @Value("${custom-config.log-aspects.log-services.exceptions}")
    private boolean logExceptions;

    /**
     * This method responsible for logging method calls
     * from {@code by.feodorkek.flights.service.impl} package
     * <p>
     * It's calling before methods executing, and if
     * {@code logCalls} value set to {@code true},
     * it execute logCall method from {@link LogController}
     *
     * @param joinPoint reflective data of the calling method
     */
    @Before(pointcutExpression)
    public void beforeMethod(JoinPoint joinPoint) {
        if (logCalls) {
            logController.logCall(log, joinPoint);
        }
    }

    /**
     * This method responsible for logging exceptions in method calls
     * from {@code by.feodorkek.flights.service.impl} package
     * <p>
     * It's calling after throwing exception, and if
     * {@code logExceptions} value set to {@code true},
     * it execute logException method from {@link LogController}
     *
     * @param joinPoint reflective data of the calling method
     * @param exception an exception occurred while executing the target method
     */
    @AfterThrowing(pointcut = pointcutExpression, throwing = "exception")
    public void exceptionMethod(JoinPoint joinPoint, Exception exception) {
        if (logExceptions) {
            logController.logException(log, joinPoint, exception);
        }
    }

}