package com.example.TiliShopBE.repository;

import com.example.TiliShopBE.model.FishSauce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FishSauceRepository extends JpaRepository<FishSauce, Long> {
}
