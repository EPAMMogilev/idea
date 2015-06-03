package com.epam.idea.rest.aspect;

import com.epam.idea.logger.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Arrays;

/**
 * Created by Aliaksei_Liubetski on 2015-06-02.
 */
@Aspect
public class ControllersAspect {

    private Log log = new Log(ControllersAspect.class);

    private String logPatternBefore = "Before: Request. Class:\"%s\" Method:\"%s\", with parameters: %s";
    private String logPatternAfter = "After: Class:\"%s\" Method:\"%s\"";

    @Before("execution(* com.epam.idea.rest.controller..*(..))")
    public void logBefore(JoinPoint joinPoint){
        log.log(
                String.format(logPatternBefore,
                        (joinPoint != null && joinPoint.getThis() != null)?joinPoint.getThis().getClass().getName():null,
                        (joinPoint != null && joinPoint.getSignature() != null)?joinPoint.getSignature().getName():null,
                        Arrays.asList(joinPoint.getArgs())
                        )
        );
    }//logBefore()

    @After("execution(* com.epam.idea.rest.controller..*(..))")
    public void logAfter(JoinPoint joinPoint){
        log.log(
                String.format(logPatternAfter,
                        (joinPoint != null && joinPoint.getThis() != null)?joinPoint.getThis().getClass().getName():null,
                        (joinPoint != null && joinPoint.getSignature() != null)?joinPoint.getSignature().getName():null
                )
        );
    }//logAfter()

}
