package io.security.corespringsecurity2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.security.corespringsecurity2.domain.entity.*;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String name);

    @Override
    void delete(Role role);
}
