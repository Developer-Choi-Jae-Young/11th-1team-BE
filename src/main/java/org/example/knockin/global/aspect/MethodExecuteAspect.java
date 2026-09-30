package org.example.knockin.global.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class MethodExecuteAspect {

    @Around("execution(public * org.example.knockin..service..*(..))")
    public Object measureService(ProceedingJoinPoint joinPoint) throws Throwable {
        return measure(joinPoint);
    }

    @Around("@annotation(org.example.knockin.global.aspect.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        return measure(joinPoint);
    }

    private  Object measure(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();

        try {
            return joinPoint.proceed();
        } finally {
            long endTime = System.nanoTime();
            double executionTimeMs = (endTime - startTime) / 1_000_000.0;

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();

            log.info(
                    "[Execution Time] {}.{} - {} ms",
                    signature.getDeclaringType().getSimpleName(),
                    signature.getName(),
                    executionTimeMs
            );
        }
    }
}
