package com.example.TiliShopBE.controller;

import com.example.TiliShopBE.entity.Account;
import com.example.TiliShopBE.model.request.LoginRequest;
import com.example.TiliShopBE.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
//@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/api/account")
    public ResponseEntity register(@Valid @RequestBody Account account) {
        Account newAccount = authenticationService.register(account);
        return ResponseEntity.ok(newAccount);
    }

    @PostMapping("/api/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest) {
        Account account = authenticationService.login(loginRequest);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/api/account")
    public ResponseEntity getAllAccount() {
        return  ResponseEntity.ok(authenticationService.getAllAccount());
    }

//    @PostMapping("/signup")
//    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
//        try {
//            Map<String, Object> response = authenticationService.signup(signupRequest);
//            return ResponseEntity.status(HttpStatus.CREATED).body(response);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
//        }
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
//        try {
//            Map<String, Object> response = authenticationService.login(loginRequest);
//            return ResponseEntity.ok(response);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
//        }
//    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully");
        return ResponseEntity.ok(response);
    }

    // Helper class for error responses
    public static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}
