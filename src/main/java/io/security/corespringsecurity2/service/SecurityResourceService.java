package io.security.corespringsecurity2.service;

import io.security.corespringsecurity2.domain.entity.AccessIp;
import io.security.corespringsecurity2.domain.entity.Resources;
import io.security.corespringsecurity2.repository.AccessIpRepository;
import io.security.corespringsecurity2.repository.ResourcesRepository;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

@Service
public class SecurityResourceService {

    private ResourcesRepository resourcesRepository;
    private AccessIpRepository accessIpRepository;

    // AppConfig 에서 bean 으로 만들고 주입하기 위해 @Component 를 사용하지 않고 생성자를 선언한다.
    public SecurityResourceService(ResourcesRepository resourcesRepository,
        AccessIpRepository accessIpRepository) {
        this.resourcesRepository = resourcesRepository;
        this.accessIpRepository = accessIpRepository;
    }

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {
        // DB에서 조회한 권한정보 매핑
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<Resources> resourcesList = resourcesRepository.findAllResources();
        resourcesList.forEach(re -> {
            List<ConfigAttribute> configAttributeList = new ArrayList<>();
            re.getRoleSet().forEach(role -> {
                configAttributeList.add(new SecurityConfig(role.getRoleName()));
                result.put(new AntPathRequestMatcher(re.getResourceName()), configAttributeList);
            });
        });
        return result;
    }

    public List<String> getAccessIpList() {
        List<AccessIp> accessIps = accessIpRepository.findAll();
        return accessIps.stream()
            .map(accessIp -> accessIp.getIpAddress())
            .collect(Collectors.toList());
    }
}
