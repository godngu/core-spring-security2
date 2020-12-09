package io.security.corespringsecurity2.security.service;

import io.security.corespringsecurity2.domain.entity.Account;
import io.security.corespringsecurity2.repository.UserRepository;
import io.security.corespringsecurity2.service.UserService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = userRepository.findByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        Set<String> userRoles = account.getUserRoles()
            .stream()
            .map(userRole -> userRole.getRoleName())
            .collect(Collectors.toSet());

        List<GrantedAuthority> collect = userRoles.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        return new AccountContext(account, collect);
    }

}
