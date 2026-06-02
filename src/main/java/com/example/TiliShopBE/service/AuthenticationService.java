package com.example.TiliShopBE.service;

import com.example.TiliShopBE.entity.Account;
import com.example.TiliShopBE.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    PasswordEncoder passwordEncoder;

    public Account register(Account account) {
        //xử lý đăng ký tài khoản
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        // Lưu tài khoản vào cơ sở dữ liệu
        return authenticationRepository.save(account);
    }
    public List<Account> getAllAccount() {
        List<Account> accounts = authenticationRepository.findAll();
        return accounts;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
