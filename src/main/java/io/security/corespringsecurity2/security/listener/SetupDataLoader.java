package io.security.corespringsecurity2.security.listener;

import io.security.corespringsecurity2.domain.entity.Account;
import io.security.corespringsecurity2.domain.entity.Resources;
import io.security.corespringsecurity2.domain.entity.Role;
import io.security.corespringsecurity2.repository.ResourcesRepository;
import io.security.corespringsecurity2.repository.RoleRepository;
import io.security.corespringsecurity2.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResourcesRepository resourcesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static AtomicInteger count = new AtomicInteger(0);

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        setupSecurityResources();

        alreadySetup = true;
    }

    private void setupSecurityResources() {
        Set<Role> roles = new HashSet<>();
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", "관리자");
        roles.add(adminRole);
        createResourceIfNotFound("/admin/**", "", roles, "url");
        Account account = createUserIfNotFound("admin", "pass", "admin@gmail.com", 10, roles);

//        createResourceIfNotFound("execution(public * io.security.corespringsecurity.aopsecurity.*Service.pointcut*(..))", "", roles, "pointcut");
//        createUserIfNotFound("admin", "admin@admin.com", "pass", roles);
//        Role managerRole = createRoleIfNotFound("ROLE_MANAGER", "매니저권한");
//        Role userRole = createRoleIfNotFound("ROLE_USER", "사용자권한");
//        createRoleHierarchyIfNotFound(managerRole, adminRole);
//        createRoleHierarchyIfNotFound(userRole, managerRole);
    }

    @Transactional
    public Role createRoleIfNotFound(String roleName, String roleDesc) {

        Role role = roleRepository.findByRoleName(roleName);

        if (role == null) {
            role = Role.builder()
                .roleName(roleName)
                .roleDesc(roleDesc)
                .build();
        }
        return roleRepository.save(role);
    }

    @Transactional
    public Account createUserIfNotFound(String userName, final String password, final String email, int age,
        Set<Role> roleSet) {

        Account account = userRepository.findByUsername(userName);

        if (account == null) {
            account = Account.builder()
                .username(userName)
                .email(email)
                .age(age)
                .password(passwordEncoder.encode(password))
                .userRoles(roleSet)
                .build();
        }
        return userRepository.save(account);
    }

    @Transactional
    public Resources createResourceIfNotFound(String resourceName, String httpMethod, Set<Role> roleSet,
        String resourceType) {
        Resources resources = resourcesRepository.findByResourceNameAndHttpMethod(resourceName, httpMethod);

        if (resources == null) {
            resources = Resources.builder()
                .resourceName(resourceName)
                .roleSet(roleSet)
                .httpMethod(httpMethod)
                .resourceType(resourceType)
                .orderNum(count.incrementAndGet())
                .build();
        }
        return resourcesRepository.save(resources);
    }
}
