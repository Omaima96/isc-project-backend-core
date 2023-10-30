package com.isc.core.utils.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;


@Log4j2
@Aspect
@Component
public class AspectUtil {

    @Autowired
    LoggerManager loggerManager;

    @Autowired
    private AuditorAware<String> auditorAware;

    @Pointcut("execution(* com.isc.authentication.controller.*.*(..))")
    public void point() {

    }

    @Before("point()")
    public void logBefore(JoinPoint joinPoint) throws JsonProcessingException {
        loggerManager.publishCreateLogEventBefore(new MessageLogBefore(joinPoint, auditorAware.getCurrentAuditor().get()));
    }


    @AfterReturning(pointcut = "point()", returning = "response")
    public void logAfter(JoinPoint joinPoint, Object response) throws JsonProcessingException {
        loggerManager.publishCreateLogEventAfter(new MessageLogAfter(joinPoint, response, auditorAware.getCurrentAuditor().get()));
    }

    @AfterThrowing(pointcut = "point()",
            throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) throws ClassNotFoundException {
        loggerManager.publishCreateLogEventAfterThrow(new MessageLogAfter(joinPoint, error.getMessage(), auditorAware.getCurrentAuditor().get()));

    }

}
