package com.example.TiliShopBE.controller;

import com.example.TiliShopBE.model.FishSauce;
import com.example.TiliShopBE.repository.FishSauceRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fish-sauce")
public class FishSauceController {

    @Autowired
    private FishSauceRepository fishSauceRepository;

    @GetMapping
    public ResponseEntity<List<FishSauce>> getAllFishSauces(){
        List<FishSauce> fishSauces = fishSauceRepository.findAll();
        return ResponseEntity.ok(fishSauces);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FishSauce> getFishSauceById(@PathVariable Long id){
        Optional<FishSauce> fishSauce = fishSauceRepository.findById(id);
        if (fishSauce.isPresent()) {
            return ResponseEntity.ok(fishSauce.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<FishSauce> createFishSauce(@Valid @RequestBody FishSauce fishSauce) {
        FishSauce savedFishSauce = fishSauceRepository.save(fishSauce);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFishSauce);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FishSauce> updateFishSauce(@PathVariable Long id, @Valid @RequestBody FishSauce fishSauceDetails) {
        Optional<FishSauce> fishSauce = fishSauceRepository.findById(id);
        if (fishSauce.isPresent()) {
            FishSauce updatedFishSauce = fishSauce.get();
            updatedFishSauce.setName(fishSauceDetails.getName());
            updatedFishSauce.setDescription(fishSauceDetails.getDescription());
            updatedFishSauce.setPrice(fishSauceDetails.getPrice());
            FishSauce savedFishSauce = fishSauceRepository.save(updatedFishSauce);
            return ResponseEntity.ok(savedFishSauce);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFishSauce(@PathVariable Long id) {
        Optional<FishSauce> fishSauce = fishSauceRepository.findById(id);
        if (fishSauce.isPresent()) {
            fishSauceRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


