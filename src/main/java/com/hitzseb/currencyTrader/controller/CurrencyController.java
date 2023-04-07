package com.hitzseb.currencyTrader.controller;

import com.hitzseb.currencyTrader.model.Currency;
import com.hitzseb.currencyTrader.service.CurrencyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/currency")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService service;

    @GetMapping
    public List<Currency> getAllCurrencies() {
        return service.getAllCurrencies();
    }

    @PostMapping("/admin/save")
    public ResponseEntity<?> saveCurrency(@RequestBody Currency currency) {
        try {
            Currency currencyResponse = service.saveCurrency(currency);
            return ResponseEntity.ok(currencyResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/admin/{id}/edit")
    public ResponseEntity<?> editMarket(@PathVariable Long id,
                                        @RequestParam Optional<String> name,
                                        @RequestParam Optional<String> code,
                                        @RequestParam Optional<String> symbol) {
        try {
            Currency currency = service.editCurrency(id, name, code, symbol);
            return ResponseEntity.ok(currency);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/admin/{id}/delete")
    public ResponseEntity<?> deleteMarket(@PathVariable Long id) {
        try {
            service.deleteCurrencyById(id);
            return ResponseEntity.ok("Successfully deleted currency with id: " + id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
