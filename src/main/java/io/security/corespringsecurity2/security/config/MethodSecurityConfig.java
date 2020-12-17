package io.security.corespringsecurity2.security.config;

import io.security.corespringsecurity2.security.factory.MethodResourcesFactoryBean;
import io.security.corespringsecurity2.service.SecurityResourceService;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Autowired
    private SecurityResourceService securityResourceService;

    @Override
    protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
        return mapBasedMethodSecurityMetadataSource();
    }

    @Bean
    public MapBasedMethodSecurityMetadataSource mapBasedMethodSecurityMetadataSource() {
        return new MapBasedMethodSecurityMetadataSource(methodResourcesMapFactoryBean().getObject());
    }

    @Bean
    public MethodResourcesFactoryBean methodResourcesMapFactoryBean() {
        MethodResourcesFactoryBean methodREsourcesFactoryBean = new MethodResourcesFactoryBean();
        methodREsourcesFactoryBean.setSecurityResourceService(securityResourceService);
        methodREsourcesFactoryBean.setResourceType("method");
        return methodREsourcesFactoryBean;
    }

    @Bean
    public MethodResourcesFactoryBean pointcutResourcesMapFactoryBean() {
        MethodResourcesFactoryBean methodREsourcesFactoryBean = new MethodResourcesFactoryBean();
        methodREsourcesFactoryBean.setSecurityResourceService(securityResourceService);
        methodREsourcesFactoryBean.setResourceType("pointcut");
        return methodREsourcesFactoryBean;
    }

    @Bean
    BeanPostProcessor protectPointcutPostProcessor() throws Exception {

        Class<?> clazz = Class.forName("org.springframework.security.config.method.ProtectPointcutPostProcessor");
        Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(MapBasedMethodSecurityMetadataSource.class);
        declaredConstructor.setAccessible(true);
        Object instance = declaredConstructor.newInstance(mapBasedMethodSecurityMetadataSource());
        Method setPointcutMap = instance.getClass().getMethod("setPointcutMap", Map.class);
        setPointcutMap.setAccessible(true);
        setPointcutMap.invoke(instance, pointcutResourcesMapFactoryBean().getObject());

        return (BeanPostProcessor) instance;
    }
}
