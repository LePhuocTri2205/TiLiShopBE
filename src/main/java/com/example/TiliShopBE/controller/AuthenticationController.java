package com.example.TiliShopBE.controller;

import com.example.TiliShopBE.model.request.LoginRequest;
import com.example.TiliShopBE.model.request.RegisterRequest;
import com.example.TiliShopBE.model.response.AccountResponse;
import com.example.TiliShopBE.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@SecurityRequirement(name = "api")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    /**
     * Đăng ký tài khoản mới.
     * Body: { "phoneNumber": "0901234567", "password": "...", "fullName": "...", "email": "..." }
     */
    @PostMapping("/api/register")
    public ResponseEntity<AccountResponse> register(@Valid @RequestBody RegisterRequest request) {
        AccountResponse response = authenticationService.register(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Đăng nhập bằng số điện thoại + mật khẩu.
     * Body: { "phoneNumber": "0901234567", "password": "..." }
     */
    @PostMapping("/api/login")
    public ResponseEntity<AccountResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AccountResponse response = authenticationService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/account")
    public ResponseEntity<List<AccountResponse>> getAllAccount() {
        return ResponseEntity.ok(authenticationService.getAllAccount());
    }

    @GetMapping("/api/account/current")
    public ResponseEntity<AccountResponse> getCurrentAccount() {
        return ResponseEntity.ok(authenticationService.getCurrentAccount());
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Đăng xuất thành công");
        return ResponseEntity.ok(response);
    }
}
