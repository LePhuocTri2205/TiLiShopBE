package com.example.TiliShopBE.controller;

import com.example.TiliShopBE.model.FishSauce;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FishSauceController {

    List<FishSauce> fishSauceList = new ArrayList<>();

    @GetMapping("api/fish-sauce")
    public ResponseEntity get(){
        return ResponseEntity.ok(fishSauceList);
    }

    @PostMapping("api/fish-sauce")
    public ResponseEntity create(@RequestBody FishSauce fishSauce) {
        fishSauceList.add(fishSauce);
        return ResponseEntity.ok(fishSauce);
    }
}

