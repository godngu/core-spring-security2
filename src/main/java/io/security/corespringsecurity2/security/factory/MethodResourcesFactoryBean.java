package io.security.corespringsecurity2.security.factory;

import io.security.corespringsecurity2.service.SecurityResourceService;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;

public class MethodResourcesFactoryBean implements FactoryBean<LinkedHashMap<String, List<ConfigAttribute>>> {

    private String resourceType;
    private SecurityResourceService securityResourceService;
    private LinkedHashMap<String, List<ConfigAttribute>> resourceMap;

    public void setSecurityResourceService(SecurityResourceService securityResourceService) {
        this.securityResourceService = securityResourceService;
    }

    @Override
    public LinkedHashMap<String, List<ConfigAttribute>> getObject() {
        if (resourceMap == null) {
            init();
        }
        return resourceMap;
    }

    private void init() {
        if ("method".equals(resourceType)) {
            this.resourceMap = securityResourceService.getMethodList();
        } else if ("pointcut".equals(resourceType)) {
            this.resourceMap = securityResourceService.getPointcutResourceList();
        }

    }

    @Override
    public Class<?> getObjectType() {
        return LinkedHashMap.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
}
