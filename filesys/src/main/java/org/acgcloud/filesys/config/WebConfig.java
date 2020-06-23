package org.acgcloud.filesys.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/acgcloud/**").addResourceLocations("classpath:/static/acgcloud/");
//        registry.addResourceHandler("/**").addResourceLocations("/","classpath:/");
        super.addResourceHandlers(registry);
    }
}
