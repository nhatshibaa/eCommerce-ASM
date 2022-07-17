package com.example.ecommerceasm.service;

import com.example.ecommerceasm.entity.Account;
import com.example.ecommerceasm.entity.Role;
import com.example.ecommerceasm.entity.auth.AccountDTO;
import com.example.ecommerceasm.entity.auth.CredentialDTO;
import com.example.ecommerceasm.entity.auth.LoginDTO;
import com.example.ecommerceasm.entity.auth.RegisterDTO;
import com.example.ecommerceasm.enums.AccountStatus;
import com.example.ecommerceasm.repository.AccountRepository;
import com.example.ecommerceasm.repository.RoleRepository;
import com.example.ecommerceasm.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    final PasswordEncoder passwordEncoder;

    public Account register(AccountDTO accountDTO){
        Account account = Account.builder()
                .username(accountDTO.getUsername())
                .passwordHash(passwordEncoder.encode(accountDTO.getPasswordHash()))
                .build();
        return accountRepository.save(account);
    }

    public Account login(LoginDTO loginDTO){
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> accountOptional = accountRepository.findAccountByUsername(username);
        Account account = accountOptional.orElse(null);
        if (account == null) {
            throw new UsernameNotFoundException("User not found in database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role:
                account.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        UserDetails userDetails = new User(account.getUsername(), account.getPasswordHash(), authorities);
        return userDetails;
    }

    public boolean matchPassword(String rawPassword, String hashPassword){
        return passwordEncoder.matches(rawPassword, hashPassword);
    }

    public CredentialDTO generateCredential(UserDetails userDetail, HttpServletRequest request) {
        String accessToken = JWTUtil.generateToken(userDetail.getUsername(),
                userDetail.getAuthorities().iterator().next().getAuthority(),
                request.getRequestURI().toString(),
                JWTUtil.ONE_DAY * 7);

        String refreshToken = JWTUtil.generateToken(userDetail.getUsername(),
                userDetail.getAuthorities().iterator().next().getAuthority(),
                request.getRequestURI().toString(),
                JWTUtil.ONE_DAY * 14);
        return new CredentialDTO(accessToken, refreshToken);
    }

    public AccountDTO saveAccount(RegisterDTO registerDTO) {
        //create new user role if not exist
        Set<Role> roles = new HashSet<>();
        for (Role role: registerDTO.getRoles()) {
            Optional<Role> userRoleOptional = roleRepository.findByName(role.getName());
            Role userRole = userRoleOptional.orElse(null);
            if (userRole == null) {
                //create new role
//            userRole = roleRepository.save(new Role(USER_ROLE));
                return null;
            }
            roles.add(userRoleOptional.get());
        }

        //check if username has exist
        Optional<Account> byUsername = accountRepository.findAccountByUsername(registerDTO.getUsername());
        if (byUsername.isPresent()) {
            return null;
        }

        Account account = new Account();

        account.setUsername(registerDTO.getUsername());
        account.setPasswordHash(passwordEncoder.encode(registerDTO.getPasswordHash()));
        account.setStatus(AccountStatus.ACTIVE);
        account.setRoles(roles);
        Account save = accountRepository.save(account);
        return new AccountDTO(save);
    }
}
