package com.benchwarmers.grads.grizzlystoreuser.security;

import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    Account_Repository accountRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException
    {
        Account account = accountRepository.findAccountByAccountEmailAddress(usernameOrEmail);
        return UserPrincipal.create(account);
    }

    @Transactional
    public UserDetails loadUserById(UUID id) {
        Account account = accountRepository.findByIdAccount(id);
        return UserPrincipal.create(account);
    }

}
