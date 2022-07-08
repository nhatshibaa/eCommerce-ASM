package com.example.ecommerceasm.entity;


import com.example.ecommerceasm.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String passwordHash;
    private Integer role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AccountStatus status;
}
