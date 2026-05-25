package com.example.TiliShopBE.controller;

import com.example.TiliShopBE.model.RicePaper;
import com.example.TiliShopBE.repository.RicePaperRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rice-papers")
public class RicePaperController {

    @Autowired
    private RicePaperRepository ricePaperRepository;

    @GetMapping
    public ResponseEntity<List<RicePaper>> getAllRicePapers() {
        List<RicePaper> ricePapers = ricePaperRepository.findAll();
        return ResponseEntity.ok(ricePapers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RicePaper> getRicePaperById(@PathVariable Long id) {
        Optional<RicePaper> ricePaper = ricePaperRepository.findById(id);
        if (ricePaper.isPresent()) {
            return ResponseEntity.ok(ricePaper.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<RicePaper> createRicePaper(@Valid @RequestBody RicePaper ricePaper) {
        RicePaper savedRicePaper = ricePaperRepository.save(ricePaper);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRicePaper);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RicePaper> updateRicePaper(@PathVariable Long id, @Valid @RequestBody RicePaper ricePaperDetails) {
        Optional<RicePaper> ricePaper = ricePaperRepository.findById(id);
        if (ricePaper.isPresent()) {
            RicePaper updatedRicePaper = ricePaper.get();
            updatedRicePaper.setName(ricePaperDetails.getName());
            updatedRicePaper.setDescription(ricePaperDetails.getDescription());
            updatedRicePaper.setPrice(ricePaperDetails.getPrice());
            RicePaper savedRicePaper = ricePaperRepository.save(updatedRicePaper);
            return ResponseEntity.ok(savedRicePaper);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRicePaper(@PathVariable Long id) {
        Optional<RicePaper> ricePaper = ricePaperRepository.findById(id);
        if (ricePaper.isPresent()) {
            ricePaperRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
