package com.hitzseb.currencyTrader.controller;

import com.hitzseb.currencyTrader.model.Market;
import com.hitzseb.currencyTrader.service.MarketService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/market")
@RequiredArgsConstructor
public class MarketController {
    private final MarketService service;

    @GetMapping
    public List<Market> getAllMarkets() {
        return service.getAllMarkets();
    }

    @PostMapping("/admin/save")
    public ResponseEntity<?> saveMarket(@RequestBody Market market) {
        try {
            Market marketResponse = service.saveMarket(market);
            return ResponseEntity.ok(marketResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/admin/{id}/edit")
    public ResponseEntity<?> editMarket(@PathVariable Long id,
                                        @RequestParam Optional<String> name,
                                        @RequestParam Optional<String> code,
                                        @RequestBody Optional<String> currencyCode) {
        try {
            Market market = service.editMarket(id, name, code, currencyCode);
            return ResponseEntity.ok(market);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/admin/{id}/delete")
    public ResponseEntity<?> deleteMarket(@PathVariable Long id) {
        try {
            service.deleteMarketById(id);
            return ResponseEntity.ok("Successfully deleted market with id: " + id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
