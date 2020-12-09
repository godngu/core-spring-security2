package io.security.corespringsecurity2.service;

import io.security.corespringsecurity2.domain.entity.Role;
import java.util.List;

public interface RoleService {

    Role getRole(long id);

    List<Role> getRoles();

    void createRole(Role role);

    void deleteRole(Long id);
}
