package org.acgcloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"org.nrocn.friday.config","org.nrocn.user", "org.acgcloud.dmsys.config"})
@EnableFeignClients(basePackages = "org.acgcloud.dmsys.services.feign")
@EnableJpaRepositories(basePackages = "org.acgcloud.dmsys.dao")
@MapperScan("org.acgcloud.dmsys.mapper")
public class AppConfig {
}
