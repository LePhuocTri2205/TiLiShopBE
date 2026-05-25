package com.example.TiliShopBE.repository;

import com.example.TiliShopBE.model.RicePaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RicePaperRepository extends JpaRepository<RicePaper, Long> {
}
