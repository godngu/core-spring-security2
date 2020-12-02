package io.security.corespringsecurity2.service.impl;

import io.security.corespringsecurity2.domain.Account;

public interface UserService {

    void createUser(
        Account account);
}