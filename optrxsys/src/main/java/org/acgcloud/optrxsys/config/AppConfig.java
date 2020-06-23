package org.acgcloud.optrxsys.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"org.nrocn.friday.config","org.nrocn.user","org.acgcloud.config"})
public class AppConfig {
}
