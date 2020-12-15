package io.security.corespringsecurity2.repository;

import io.security.corespringsecurity2.domain.entity.RoleHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleHierarchyRepository extends JpaRepository<RoleHierarchy, Long> {

    RoleHierarchy findByRoleName(String roleName);
}
