package org.acgcloud.filesys.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "filesys")
@Data
public class FileSys {
    private String path;
}
