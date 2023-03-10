package com.hitzseb.currencyTrader.controller;

import com.hitzseb.currencyTrader.response.VariationResponse;
import com.hitzseb.currencyTrader.service.VariationService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class VariationController {
    @Autowired
    VariationService variationService;

    @io.swagger.v3.oas.annotations.Operation(summary = "Calculates the exchange rate variation of a currency " +
            "in a market since a specified date and returns the variation rate and associated value records.")
    @GetMapping("/variation")
    public ResponseEntity<?> getExchangeRateVariation(
            @Parameter(description = "Currency iso code")
            @RequestParam("currency") Optional<String> currencyCode,
            @Parameter(description = "Market code: e.g. ARG")
            @RequestParam("market") Optional<String> marketCode,
            @Parameter(description = "YYYY-MM-DD format date")
            @RequestParam("date")Optional<LocalDate> registeredAt) {
        if (!(currencyCode.isPresent() && marketCode.isPresent() && registeredAt.isPresent())) {
            return ResponseEntity.badRequest().body(Constants.EMPTY_PARAM);
        }
        try {
            VariationResponse variation = variationService.getExchangeRateVariationResponse(currencyCode.get(),
                    marketCode.get(), registeredAt.get());
            return ResponseEntity.ok(variation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Constants.INVALID_PARAM);
        }
    }
}
