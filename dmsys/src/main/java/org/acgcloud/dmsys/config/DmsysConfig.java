package org.acgcloud.dmsys.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(value = "acgcsloud.dmsys")
@Data
public class DmsysConfig {
    private String dependOnUri;
}
