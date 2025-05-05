package com.ossproj.donjjul.controller;

import com.ossproj.donjjul.domain.Store;
import com.ossproj.donjjul.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<Store> createStore(@RequestBody Store store) {
        Store saved = storeService.createStore(store);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{businessNumber}")
    public ResponseEntity<Store> getStoreByBusinessNumber(@PathVariable String businessNumber) {
        Optional<Store> store = storeService.findByBusinessNumber(businessNumber);
        return store.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
