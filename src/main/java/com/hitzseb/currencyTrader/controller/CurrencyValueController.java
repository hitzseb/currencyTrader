package com.hitzseb.currencyTrader.controller;

import com.hitzseb.currencyTrader.model.CurrencyValue;
import com.hitzseb.currencyTrader.service.CurrencyValueService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/value")
@RequiredArgsConstructor
public class CurrencyValueController {
    private final CurrencyValueService service;

    @GetMapping
    public ResponseEntity<?> getAllValues(@RequestParam("currency") Optional<String> currencyCode,
                                          @RequestParam("market") Optional<String> marketCode,
                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                          @RequestParam(value = "size", defaultValue = "10") int size) {
        if (!currencyCode.isPresent()) {
            return ResponseEntity.badRequest().body("Parameter currency is missing.");
        }
        if (!marketCode.isPresent()) {
            return ResponseEntity.badRequest().body("Parameter market is missing.");
        }
        try {
            Page<CurrencyValue> response = service.getAllCurrencyValues(page, size, currencyCode.get(), marketCode.get());
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/admin/save")
    public ResponseEntity<?> saveCurrencyValue(@RequestBody CurrencyValue currencyValue) {
        try {
            CurrencyValue value = service.saveCurrencyValue(currencyValue);
            return ResponseEntity.ok(value);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/admin/{id}/edit")
    public ResponseEntity<?> editCurrencyValue(@PathVariable Long id,
                                               @RequestParam Optional<LocalDate> registeredAt,
                                               @RequestParam Optional<Double> purchaseValue,
                                               @RequestParam Optional<Double> saleValue,
                                               @RequestParam Optional<Boolean> isActive) {
        try {
            CurrencyValue value = service.editCurrencyValue(id, registeredAt, purchaseValue, saleValue, isActive);
            return ResponseEntity.ok(value);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/admin/{id}/delete")
    public ResponseEntity<?> deleteCurrencyValue(@PathVariable Long id) {
        try {
            service.deleteCurrencyValueById(id);
            return ResponseEntity.ok("Successfully deleted currency value with id: " + id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
