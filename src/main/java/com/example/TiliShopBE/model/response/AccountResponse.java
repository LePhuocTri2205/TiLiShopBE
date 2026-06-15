package com.example.TiliShopBE.model.response;

import lombok.Data;

@Data
public class AccountResponse {
    private Long id;
    private String email;
    private String phoneNumber;
    private String fullName;
    private String token;
}
