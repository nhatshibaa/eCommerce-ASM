package com.example.ecommerceasm.entity;


import com.example.ecommerceasm.enums.UserStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @Id
    private String id;
    private String fullname;
    private String phone;
    private String address;
    @Column(name = "email", unique = true)
    private String email;
    private String city;
    private String district;
    private String ward;
    private UserStatus status;
    @OneToOne
    private Account username;
    @OneToOne
    private Account password;


}
