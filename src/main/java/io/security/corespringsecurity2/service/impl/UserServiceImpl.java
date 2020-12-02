package io.security.corespringsecurity2.service.impl;

import io.security.corespringsecurity2.domain.Account;
import io.security.corespringsecurity2.repository.UserRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void createUser(Account account) {
        userRepository.save(account);
    }
}
