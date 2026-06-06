package com.example.TiliShopBE.model.response;

import lombok.Data;

@Data
public class AccountResponse {
    private Long id;
    private String email;
    private String username;
    private String fullName;
    private String phone;
    private String token;
}
