package org.acgcloud.config.config;

import lombok.Data;
import lombok.SneakyThrows;
import org.nrocn.lib.utils.BaseString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ObjectUtils;
import org.acgcloud.config.annotation.MServer;

import java.lang.reflect.Method;


@Data
public abstract class AbstractAcgCloudConfig extends SpringBootServletInitializer implements WebServerFactoryCustomizer {

    @Autowired
    private  AppConfiguration appConfiguration;


    @Autowired
    private ApplicationContext applicationContext;


    private AbstractAppConfig abstractAppConfig;

    @Value("${server.port}")
    private  Integer defaultPort;



    private String microServicesId;


    //设置微服务配置
    public void serverProperties(){

    }





    @SneakyThrows
    public void annotationPropertiseConfig(){
        String[] beanNamesForType = applicationContext.getBeanNamesForAnnotation(MServer.class);
        for (String beanName : beanNamesForType) {
            Object mserver = applicationContext.getBean(beanName);
            MServer annotation = mserver.getClass().getAnnotation(MServer.class);
            String serverId = annotation.serverId();
            //首字母大写
            serverId = BaseString.firstUpperLetter(serverId);
            //获得方法调用
            Method method = appConfiguration.getClass()
                    .getMethod(BaseString.GETTER + serverId);
            AbstractAppConfig appConfig = (AbstractAppConfig) method.invoke(appConfiguration);
            this.abstractAppConfig = appConfig;
            break;
        }

    }

    public void refresh(){
        serverProperties();
        annotationPropertiseConfig();
    }


    @Override
    public void customize(WebServerFactory factory) {

    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryWebServerFactoryCustomizer(){
        refresh();
        return  new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                if(!ObjectUtils.isEmpty(abstractAppConfig.getPort())){
                    factory.setPort(getAbstractAppConfig().getPort());
                }
            }
        };
    }
}
