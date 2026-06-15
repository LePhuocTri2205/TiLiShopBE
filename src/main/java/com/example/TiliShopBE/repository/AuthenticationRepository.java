package com.example.TiliShopBE.repository;

import com.example.TiliShopBE.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepository extends JpaRepository<Account, Long> {

    // Tìm account theo số điện thoại (định danh chính)
    Account findAccountByPhoneNumber(String phoneNumber);

    // Kiểm tra số điện thoại đã tồn tại chưa (dùng khi đăng ký)
    boolean existsByPhoneNumber(String phoneNumber);

    Account findAccountById(long id);
}
