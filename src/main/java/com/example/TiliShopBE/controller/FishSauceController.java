package com.example.TiliShopBE.controller;

import com.example.TiliShopBE.entity.FishSauce;
import com.example.TiliShopBE.service.FishSauceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "api")
@RequestMapping("/api/fish-sauce")
public class FishSauceController {

    @Autowired
    private FishSauceService fishSauceService;

    @GetMapping
    public ResponseEntity<List<FishSauce>> getAllFishSauces() {
        return ResponseEntity.ok(fishSauceService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FishSauce> getFishSauceById(@PathVariable Long id) {
        return fishSauceService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FishSauce> createFishSauce(@Valid @RequestBody FishSauce fishSauce) {
        FishSauce saved = fishSauceService.create(fishSauce);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FishSauce> updateFishSauce(@PathVariable Long id,
                                                      @Valid @RequestBody FishSauce details) {
        return fishSauceService.update(id, details)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFishSauce(@PathVariable Long id) {
        if (fishSauceService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
