package com.example.TiliShopBE.service;

import com.example.TiliShopBE.entity.Account;
import com.example.TiliShopBE.model.request.LoginRequest;
import com.example.TiliShopBE.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Account register(Account account) {
        //xử lý đăng ký tài khoản
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        // Lưu tài khoản vào cơ sở dữ liệu
        return authenticationRepository.save(account);
    }

    public Account login(LoginRequest loginRequest) {
            // Xác thực người dùng
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            Account account = (Account) authentication.getPrincipal();
            return account;
    }

    public List<Account> getAllAccount() {
        List<Account> accounts = authenticationRepository.findAll();
        return accounts;
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
            return authenticationRepository.findByUsername(phone);
    }
}
