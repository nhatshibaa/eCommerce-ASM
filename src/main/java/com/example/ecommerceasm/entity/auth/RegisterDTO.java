package com.example.ecommerceasm.entity.auth;

import com.example.ecommerceasm.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RegisterDTO {
    private String username;
    private String passwordHash;
    private Set<Role> roles;
}
