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
import java.util.function.Function;

@Service
public class TokenService {

    private final String SECRET_KEY = "hackhotao1234567890hackhotao1234567890hackhotao1234567890hackhotao1234567890";

    @Autowired
    AuthenticationRepository authenticationRepository;

    public SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Tạo JWT với subject là phoneNumber (định danh duy nhất).
     */
    public String generateToken(Account account) {
        return Jwts.builder()
                .subject(account.getPhoneNumber())        // Subject = phoneNumber
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 10)) // 10 giờ
                .signWith(getSignInKey())
                .compact();
    }

    /**
     * Giải mã JWT, lấy phoneNumber từ subject, tìm Account trong DB.
     */
    public Account extractToken(String token) {
        String phoneNumber = extractClaim(token, Claims::getSubject);
        return authenticationRepository.findAccountByPhoneNumber(phoneNumber);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }
}
