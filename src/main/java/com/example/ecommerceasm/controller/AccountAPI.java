package com.example.ecommerceasm.controller;

import com.example.ecommerceasm.entity.Account;
import com.example.ecommerceasm.entity.auth.AccountDTO;
import com.example.ecommerceasm.entity.auth.CredentialDTO;
import com.example.ecommerceasm.entity.auth.LoginDTO;
import com.example.ecommerceasm.entity.auth.RegisterDTO;
import com.example.ecommerceasm.repository.AccountRepository;
import com.example.ecommerceasm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountAPI {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto, HttpServletRequest request) {
        UserDetails userDetail = accountService.loadUserByUsername(loginDto.getUsername());
        if (userDetail == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Account not found");
        }
        if (accountService.matchPassword(loginDto.getPassword(), userDetail.getPassword())) {
            CredentialDTO credentialDto = accountService.generateCredential(userDetail, request);
            return ResponseEntity.status(HttpStatus.OK).body(credentialDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login fails");
    }

    @RequestMapping(path = "profile", method = RequestMethod.GET)
    public ResponseEntity<?> profile(Authentication authentication){
        return ResponseEntity.status(HttpStatus.OK).body(authentication.getName());
    }

//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public ResponseEntity<Account> register(@RequestBody AccountDTO  accountDTO) {
//        Account account = accountService.register(accountDTO);
//        if (account == null){
//            return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return ResponseEntity.ok(account);
//    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestBody RegisterDTO registerDTO) {
        AccountDTO account = accountService.saveAccount(registerDTO);
        return ResponseEntity.ok().body(account);
    }
}

