package org.acgcloud.account.config;


import org.mybatis.spring.annotation.MapperScan;
import org.nrocn.user.config.UserAppConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages={"org.nrocn.user","org.nrocn.friday.config"})
public class AppConfig {
}
