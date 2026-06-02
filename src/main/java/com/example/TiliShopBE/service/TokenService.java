package com.example.TiliShopBE.service;

import com.example.TiliShopBE.entity.Account;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.security.auth.login.AccountException;
import java.util.Date;

public class TokenService {
    private final String SECRET_KEY = "hackhotao1234567890hackhotao1234567890"; // Should be at least 256 bits for HS256

    public SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Account account) {
        return Jwts.builder()
                .subject("")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))// Token valid for 10 hours
                .signWith(getSignInKey())
                .compact();
    }

    public AccountException extractToken(String token) {
        return null;
    }
}

