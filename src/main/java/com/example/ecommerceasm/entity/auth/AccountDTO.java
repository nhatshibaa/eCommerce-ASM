package com.example.ecommerceasm.entity.auth;

import com.example.ecommerceasm.entity.Account;
import com.example.ecommerceasm.entity.Role;
import com.example.ecommerceasm.enums.AccountStatus;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AccountDTO {
    private long id;
    private String username;
    private String passwordHash;
    private Set<Role> roles;
    private int status;
    public AccountDTO(Account account) {
        this.id = account.getId();
        this.username = account.getUsername();
        this.roles = account.getRoles();
    }
}
