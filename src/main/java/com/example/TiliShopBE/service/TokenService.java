package com.example.TiliShopBE.service;

import com.example.TiliShopBE.entity.Account;
import com.example.TiliShopBE.repository.AuthenticationRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class TokenService {

    private final String SECRET_KEY = "hackhotao1234567890hackhotao1234567890hackhotao1234567890hackhotao1234567890"; // Should be at least 256 bits for HS256

    @Autowired
    AuthenticationRepository authenticationRepository;

    public SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Account account) {
        return Jwts.builder()
                .subject(account.getId() + "") // Use account ID as the subject
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))// Token valid for 10 hours
                .signWith(getSignInKey())
                .compact();
    }

    public Account extractToken(String token) {
        String value = extractClaim(token,Claims::getSubject);
        long id = Long.parseLong(value);
        return authenticationRepository.findAccountById(id);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, java.util.function.Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }
}

