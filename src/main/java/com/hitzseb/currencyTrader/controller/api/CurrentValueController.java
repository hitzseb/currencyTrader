package com.hitzseb.currencyTrader.controller.api;

import com.hitzseb.currencyTrader.response.ValueResponse;
import com.hitzseb.currencyTrader.service.CurrentValueService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CurrentValueController {
    private final CurrentValueService service;

    @GetMapping("/api/v1/current")
    public ResponseEntity<?> getCurrentValue(
            @RequestParam("currency") Optional<String> currencyCode,
            @RequestParam("market") Optional<String> marketCode) {
        if (!currencyCode.isPresent()) {
            return ResponseEntity.badRequest().body("Parameter currency is missing.");
        }
        if (!marketCode.isPresent()) {
            return ResponseEntity.badRequest().body("Parameter market is missing.");
        }
        try {
            ValueResponse response = service.getCurrentValue(
                    currencyCode.get(),
                    marketCode.get());
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
