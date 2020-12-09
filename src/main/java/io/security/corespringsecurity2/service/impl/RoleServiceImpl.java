package io.security.corespringsecurity2.service.impl;

import io.security.corespringsecurity2.domain.entity.Role;
import io.security.corespringsecurity2.repository.RoleRepository;
import io.security.corespringsecurity2.service.RoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    @Override
    public Role getRole(long id) {
        return roleRepository.findById(id).orElse(new Role());
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    @Override
    public void createRole(Role role) {
        roleRepository.save(role);
    }

    @Transactional
    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

}
