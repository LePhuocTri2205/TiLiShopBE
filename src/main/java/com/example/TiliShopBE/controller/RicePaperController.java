package com.example.TiliShopBE.controller;

import com.example.TiliShopBE.entity.RicePaper;
import com.example.TiliShopBE.service.RicePaperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rice-papers")
public class RicePaperController {

    @Autowired
    private RicePaperService ricePaperService;

    @GetMapping
    public ResponseEntity<List<RicePaper>> getAllRicePapers() {
        return ResponseEntity.ok(ricePaperService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RicePaper> getRicePaperById(@PathVariable Long id) {
        return ricePaperService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RicePaper> createRicePaper(@Valid @RequestBody RicePaper ricePaper) {
        RicePaper saved = ricePaperService.create(ricePaper);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RicePaper> updateRicePaper(@PathVariable Long id,
                                                      @Valid @RequestBody RicePaper details) {
        return ricePaperService.update(id, details)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRicePaper(@PathVariable Long id) {
        if (ricePaperService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
