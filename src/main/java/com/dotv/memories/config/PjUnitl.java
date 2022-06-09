package com.dotv.memories.config;

import com.dotv.memories.entity.Account;
import com.dotv.memories.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class PjUnitl {
    @Autowired
    AccountRepository accountRepository;

    public Timestamp getDateCurr(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        //System.out.println(dtf.format(now)); //2016-11-16 12:08:43
        return Timestamp.valueOf(dtf.format(now));
    }

    public Account getAcc() throws Exception {
        Object userLogined = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userLogined != null && userLogined instanceof UserDetails) {
            return accountRepository.findByUsername(((UserDetails) userLogined).getUsername()).get(0);
        }
        return null;
    }
}
