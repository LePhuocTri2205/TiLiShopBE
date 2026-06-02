package com.example.TiliShopBE.service;

import com.example.TiliShopBE.entity.FishSauce;
import com.example.TiliShopBE.repository.FishSauceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FishSauceService {

    @Autowired
    private FishSauceRepository fishSauceRepository;

    public List<FishSauce> getAll() {
        return fishSauceRepository.findAll();
    }

    public Optional<FishSauce> getById(Long id) {
        return fishSauceRepository.findById(id);
    }

    public FishSauce create(FishSauce fishSauce) {
        return fishSauceRepository.save(fishSauce);
    }

    public Optional<FishSauce> update(Long id, FishSauce details) {
        return fishSauceRepository.findById(id).map(existing -> {
            existing.setName(details.getName());
            existing.setDescription(details.getDescription());
            existing.setPrice(details.getPrice());
            return fishSauceRepository.save(existing);
        });
    }

    public boolean delete(Long id) {
        if (fishSauceRepository.existsById(id)) {
            fishSauceRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
