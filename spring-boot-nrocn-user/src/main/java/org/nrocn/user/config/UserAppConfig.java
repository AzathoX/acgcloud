package org.nrocn.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "org.nrocn.user.dao")
@MapperScan(basePackages = "org.nrocn.user.mapper")
public class UserAppConfig {
}
