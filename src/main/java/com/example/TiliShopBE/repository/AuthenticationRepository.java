package com.example.TiliShopBE.repository;

import com.example.TiliShopBE.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepository extends JpaRepository<Account,Long> {
}
