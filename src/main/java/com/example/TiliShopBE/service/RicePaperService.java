package com.example.TiliShopBE.service;

import com.example.TiliShopBE.entity.RicePaper;
import com.example.TiliShopBE.repository.RicePaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RicePaperService {

    @Autowired
    private RicePaperRepository ricePaperRepository;

    public List<RicePaper> getAll() {
        return ricePaperRepository.findAll();
    }

    public Optional<RicePaper> getById(Long id) {
        return ricePaperRepository.findById(id);
    }

    public RicePaper create(RicePaper ricePaper) {
        return ricePaperRepository.save(ricePaper);
    }

    public Optional<RicePaper> update(Long id, RicePaper details) {
        return ricePaperRepository.findById(id).map(existing -> {
            existing.setName(details.getName());
            existing.setDescription(details.getDescription());
            existing.setPrice(details.getPrice());
            return ricePaperRepository.save(existing);
        });
    }

    public boolean delete(Long id) {
        if (ricePaperRepository.existsById(id)) {
            ricePaperRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
