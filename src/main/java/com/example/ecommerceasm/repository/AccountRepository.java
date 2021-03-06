package com.example.ecommerceasm.repository;

import com.example.ecommerceasm.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findAccountByUsername(String username);
}
