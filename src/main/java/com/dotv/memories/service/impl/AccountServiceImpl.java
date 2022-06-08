package com.dotv.memories.service.impl;

import com.dotv.memories.entity.Account;
import com.dotv.memories.repository.AccountRepository;
import com.dotv.memories.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public Optional<Account> getAcc(int id) {
        return accountRepository.findById(id);
    }
}
