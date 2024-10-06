package world.array.springboot.aop.aspects;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcutConfig {
    @Pointcut("@annotation(world.array.springboot.aop.annotations.TrackTime)")
    public void trackTimeAnnotation() {}
}
