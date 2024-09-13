package by.feodorkek.flights.aspect.logging.loggers;

import by.feodorkek.flights.aspect.logging.LogController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ControllersLogAspect {

    private final String methodsSignature = "execution(* by.feodorkek.flights.controller..*(..)) && !@annotation(by.feodorkek.flights.aspect.logging.ExcludeFromAspectLog)";
    private final LogController logController;

    @Value("${custom-config.log-aspects.log-controllers.calls}")
    private boolean logCalls;

    @Value("${custom-config.log-aspects.log-controllers.exceptions}")
    private boolean logExceptions;

    @Before(methodsSignature)
    public void beforeMethod(JoinPoint joinPoint) {
        if (logCalls) {
            logController.logCall(log, joinPoint);
        }
    }

    @AfterThrowing(pointcut = methodsSignature, throwing = "exception")
    public void exceptionMethod(JoinPoint joinPoint, Exception exception) {
        if (logExceptions) {
            logController.logException(log, joinPoint, exception);
        }
    }

}