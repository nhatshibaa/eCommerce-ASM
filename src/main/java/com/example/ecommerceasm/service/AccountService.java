package com.example.ecommerceasm.service;

import com.example.ecommerceasm.entity.Account;
import com.example.ecommerceasm.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
public class AccountService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByUsername(username);
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (account.getRole() == 1) {
            authorities.add(new SimpleGrantedAuthority("USER"));
        } else if (account.getRole() == 2) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        } else if (account.getRole() == 0) {
            authorities.add(new SimpleGrantedAuthority("GUEST"));
        }
        UserDetails userDetails = new User(account.getUsername(), account.getPasswordHash(), authorities);
        return userDetails;
    }
}
