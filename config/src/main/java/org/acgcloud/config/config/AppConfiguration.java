package org.acgcloud.config.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * 配置信息
 */
@Configuration
@ConfigurationProperties(prefix = "acgcloud")
@Data
public class AppConfiguration {

    private Dmsys dmsys;

    private Optrx optrx;

    private WebApp webapp;

    @Data
    public static class Dmsys extends  AbstractAppConfig {

        @Value("${depend-on-uri}")
        private String dependOnUri;
    }

    @Data
    public static class Optrx extends AbstractAppConfig {

        @Value("${file-path}")
        private String filePath;

        @Value("${buffer-path}")
        private String bufferPath;
    }

    @Data
    public static class WebApp extends AbstractAppConfig  {

    }

}
