package org.acgcloud.config.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 注解编程
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface MServer {
    /**
     * 服务名称
     * @return
     */
    String serverId() default  "acgcloud";

    /**
     * 端口号
     * @return
     */
    int port() default 8081;
}
