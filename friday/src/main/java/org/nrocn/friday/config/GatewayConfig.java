package org.nrocn.friday.config;


import org.nrocn.friday.filter.FridayFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayConfig {

    @Bean
    public FilterRegistrationBean firdayConfig(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new FridayFilter());
        filterRegistrationBean.setName("friday");
        filterRegistrationBean.addUrlPatterns("/role/*");
        filterRegistrationBean.addUrlPatterns("/user/main/role/*");
        return filterRegistrationBean;
    }


}
