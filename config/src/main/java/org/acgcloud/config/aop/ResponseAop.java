package org.acgcloud.config.aop;

import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.nrocn.lib.baserqnp.IMicroResponsable;
import org.nrocn.lib.baserqnp.ResultCode;
import org.springframework.stereotype.Component;
import org.acgcloud.common.dto.WebResponse;

@Aspect
@Component
public class ResponseAop {
    @Pointcut("execution(* org.acgcloud.*.controller.*.doResp*(..))")
    public void responsePc(){}

    @Around(value = "responsePc()")
    public IMicroResponsable doResponseAround(ProceedingJoinPoint joinPoint){
        try {
            Object result = joinPoint.proceed();
            if(result instanceof  IMicroResponsable){
                return (IMicroResponsable) result;
            }
            else{
                return ObjectUtils.isEmpty(result)?
                      WebResponse.getPrototype().failedResp("请求成功但返回为空",ResultCode.MSG_NOT_READABLE):
                        WebResponse.getPrototype().successResp("请求成功",result);
            }
        } catch (Throwable ex) {
            return  WebResponse.getPrototype().failedResp("请求失败", ResultCode.FAILURE,ex.getMessage());
        }

    }
}
