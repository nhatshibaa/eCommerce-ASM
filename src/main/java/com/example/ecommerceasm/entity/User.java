package com.example.ecommerceasm.entity;


import com.example.ecommerceasm.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @Id
    private String id;
    private String fullname;
    private String phone;
    private String address;
    private String email;
    private String city;
    private String district;
    private String ward;
    private String username;
    private String password;
    private UserStatus status;
}
