package world.array.springboot.aop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class TimerAspect {
    Logger logger = LoggerFactory.getLogger(getClass());

//    @Around("execution(* world.array.springboot.aop.*.*.*(..))")
    @Around("world.array.springboot.aop.aspects.CommonPointcutConfig.trackTimeAnnotation()")
    public Object findExecutionTime(ProceedingJoinPoint joinPoint) {
        long startTimeMillis= System.currentTimeMillis();

        Object returnValue = null;
        try {
            returnValue = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        long stopTimeMillis= System.currentTimeMillis();
        long executionDuration = stopTimeMillis-startTimeMillis;
        logger.info("Method is called - {} - took {} ms", joinPoint, executionDuration);
        return returnValue;
    }
}
