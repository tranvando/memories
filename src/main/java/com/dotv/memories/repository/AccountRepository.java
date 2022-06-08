package com.dotv.memories.repository;

import com.dotv.memories.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Integer> {
//    @Query("select a from Account a where a.username=?1")
    List<Account> findByUsername(String username);
}