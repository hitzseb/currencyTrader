package com.hitzseb.currencyTrader.controller;

import com.hitzseb.currencyTrader.response.ExchangeResponse;
import com.hitzseb.currencyTrader.service.ExchangeService;
import com.hitzseb.currencyTrader.enums.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ExchangeController {
    @Autowired
    ExchangeService exchangeService;

    @io.swagger.v3.oas.annotations.Operation(summary = "Converts an amount of one currency to another based on" +
            " the latest market values and returns the equivalent value in the target currency.")
    @GetMapping("/exchange")
    public ResponseEntity<?> exchangeCurrency(
            @Parameter(description = "Operation type")
            @RequestParam("operation") Optional<Operation> operation,
            @Parameter(description = "Market code; e.g. ARG")
            @RequestParam("market") Optional<String> marketCode,
            @Parameter(description = "Original currency iso code")
            @RequestParam("from") Optional<String> currencyFromCode,
            @Parameter(description = "target currency iso code")
            @RequestParam("to") Optional<String> currencyToCode,
            @Parameter(description = "Amount to exchange")
            @RequestParam("amount") Optional<Double> amount) {
        if (!(marketCode.isPresent() && currencyFromCode.isPresent() && currencyToCode.isPresent() && amount.isPresent())) {
            return ResponseEntity.badRequest().body(Constants.EMPTY_PARAM);
        }
        try {
            ExchangeResponse exchangeResponse = exchangeService.exchangeCurrency(operation.get(), marketCode.get(),
                    currencyFromCode.get(), currencyToCode.get(), amount.get());
            return ResponseEntity.ok(exchangeResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Constants.INVALID_PARAM);
        }
    }
}
