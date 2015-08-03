package com.epam.idea.rest.aspect;

import com.epam.idea.logger.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Arrays;

/**
 * Created by Aliaksei_Liubetski on 2015-06-03.
 */
@Aspect
public class ServicesAspect {

    private Log log = new Log(ServicesAspect.class);

    private String logPatternBefore = "Before: Services request. Class:\"%s\" Method:\"%s\", with parameters: %s";
    private String logPatternAfter = "After: Services. Class:\"%s\" Method:\"%s\"";

    @Before("execution(* com.epam.idea.core.service.impl..*(..))")
    public void logBefore(JoinPoint joinPoint){
        log.log(
                String.format(logPatternBefore,
                        getClassName(joinPoint),
                        getMethodName(joinPoint),
                        Arrays.asList(joinPoint.getArgs())
                )
        );
    }//logBefore()

    @After("execution(* com.epam.idea.core.service.impl..*(..))")
    public void logAfter(JoinPoint joinPoint){
        log.log(
                String.format(logPatternAfter,
                        getClassName(joinPoint),
                        getMethodName(joinPoint)
                )
        );
    }//logAfter()

    private String getClassName(JoinPoint joinPoint){
        return (joinPoint != null && joinPoint.getThis() != null)?joinPoint.getThis().getClass().getName():null;
    }//getClassName

    private String getMethodName(JoinPoint joinPoint){
        return (joinPoint != null && joinPoint.getSignature() != null)?joinPoint.getSignature().getName():null;
    }//getMethodName
}
