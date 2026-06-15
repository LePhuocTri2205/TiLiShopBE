package com.example.TiliShopBE.service;

import com.example.TiliShopBE.entity.Account;
import com.example.TiliShopBE.model.request.LoginRequest;
import com.example.TiliShopBE.model.request.RegisterRequest;
import com.example.TiliShopBE.model.response.AccountResponse;
import com.example.TiliShopBE.repository.AuthenticationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TokenService tokenService;

    /**
     * Đăng ký tài khoản mới bằng RegisterRequest.
     * Kiểm tra trùng số điện thoại trước khi lưu.
     */
    public AccountResponse register(RegisterRequest request) {
        if (authenticationRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new IllegalArgumentException("Số điện thoại đã được đăng ký. Vui lòng dùng số khác.");
        }

        Account account = new Account();
        account.setPhoneNumber(request.getPhoneNumber());
        account.setEmail(request.getEmail());
        account.setFullName(request.getFullName());
        account.setPassword(passwordEncoder.encode(request.getPassword()));

        Account saved = authenticationRepository.save(account);
        AccountResponse response = modelMapper.map(saved, AccountResponse.class);
        response.setToken(tokenService.generateToken(saved));
        return response;
    }

    /**
     * Đăng nhập bằng số điện thoại + mật khẩu.
     * UsernamePasswordAuthenticationToken nhận phoneNumber làm "username"
     * vì Account.getUsername() trả về phoneNumber.
     */
    public AccountResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getPhoneNumber(),
                        loginRequest.getPassword()
                )
        );
        Account account = (Account) authentication.getPrincipal();
        AccountResponse accountResponse = modelMapper.map(account, AccountResponse.class);
        accountResponse.setToken(tokenService.generateToken(account));
        return accountResponse;
    }

    public List<AccountResponse> getAllAccount() {
        List<Account> accounts = authenticationRepository.findAll();
        return accounts.stream()
                .map(account -> modelMapper.map(account, AccountResponse.class))
                .toList();
    }

    /**
     * Spring Security gọi method này để load UserDetails khi xác thực.
     * Tham số "username" ở đây thực chất là phoneNumber.
     */
    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Account account = authenticationRepository.findAccountByPhoneNumber(phoneNumber);
        if (account == null) {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản với số điện thoại: " + phoneNumber);
        }
        return account;
    }

    public AccountResponse getCurrentAccount() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return modelMapper.map(account, AccountResponse.class);
    }
}
