package org.acgcloud.config.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class CultimeAop {


    @Pointcut("execution(* org.acgcloud.*.controller.*.*(..))")
    public void cultimePc(){}


    @Around("cultimePc()")
    public Object cultime(ProceedingJoinPoint proceedingJoinPoint){
        Object result = null;
        //计算执行时间
        long current = System.currentTimeMillis();
        try {
            result  =  proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            System.out.println(throwable.getMessage());
        }
        long useTime = System.currentTimeMillis() - current;
        //写日志
        System.out.println(proceedingJoinPoint.getTarget() + "用时"+ useTime + "ms");
        return result;
    }
}
