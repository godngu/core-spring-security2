package io.security.corespringsecurity2.service.impl;

import io.security.corespringsecurity2.domain.entity.RoleHierarchy;
import io.security.corespringsecurity2.repository.RoleHierarchyRepository;
import io.security.corespringsecurity2.service.RoleHierarchyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleHierarchyServiceImpl implements RoleHierarchyService {

    @Autowired
    private RoleHierarchyRepository roleHierarchyRepository;

    @Override
    public String findAllHierarchy() {
        List<RoleHierarchy> roleHierarchies = roleHierarchyRepository.findAll();

        StringBuilder concatedRoles = new StringBuilder();
        roleHierarchies.forEach(roleHierarchy -> {
            if (roleHierarchy.getParentName() != null) {
                concatedRoles.append(roleHierarchy.getParentName().getRoleName());
                concatedRoles.append(" > ");
                concatedRoles.append(roleHierarchy.getRoleName());
                concatedRoles.append("\n");
            }
        });
        return concatedRoles.toString();
    }
}
