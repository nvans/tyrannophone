package com.nvans.tyrannophone.aop.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NotifyShowcaseAspect {

    @AfterReturning("@annotation(NotifyShowcase)")
    public Object notifyShowcase(ProceedingJoinPoint joinPoint) throws Throwable {

        return joinPoint.proceed();
    }

}
