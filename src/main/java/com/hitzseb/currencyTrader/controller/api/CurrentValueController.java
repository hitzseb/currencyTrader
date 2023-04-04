package com.hitzseb.currencyTrader.controller.api;

import com.hitzseb.currencyTrader.response.ValueResponse;
import com.hitzseb.currencyTrader.service.CurrentValueService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "retrieves the current value of a specified currency in a specified market.",
            description = "The CurrentValueController endpoint retrieves the current value of a specified currency in a specified market." +
                    " The endpoint requires two query parameters: `currency` and `market` codes." +
                    " If either of these parameters is missing, the endpoint will return a bad request response." +
                    " If the specified currency and market combination is not found, the endpoint will return a not found response." +
                    " Otherwise, it returns the current value of the specified currency in the specified market.")
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
