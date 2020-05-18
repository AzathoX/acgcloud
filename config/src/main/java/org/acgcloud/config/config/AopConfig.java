package org.acgcloud.config.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"org.acgcloud.*.config","org.acgcloud.*.aop"})
public class AopConfig {
}
