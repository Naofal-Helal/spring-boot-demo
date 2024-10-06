package world.array.springboot.aop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class LoggingAspect {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Before("execution(* world.array.springboot.aop.business.*.*(..))")
    public void logMethodCall(JoinPoint joinPoint) {
        logger.info("Method is called - {}", joinPoint);
    }
}
