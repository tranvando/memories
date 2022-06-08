package com.dotv.memories.service;

import com.dotv.memories.entity.Account;

import java.util.Optional;

public interface AccountService {
    Optional<Account> getAcc(int id);
}
