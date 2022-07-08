package com.example.ecommerceasm.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CredentialDTO {
    private String access_token;
    private String refresh_token;
//    private Integer expired_in;
//    private Integer refresh_expired_in;
//    private String scope;
}
