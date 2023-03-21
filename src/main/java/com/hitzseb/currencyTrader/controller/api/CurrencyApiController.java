package com.hitzseb.currencyTrader.controller.api;

import com.hitzseb.currencyTrader.service.Code;
import com.hitzseb.currencyTrader.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/currency")
public class CurrencyApiController {
    @Autowired
    CurrencyService currencyService;

    @Operation(summary = "Get all currency codes",
            description = "This endpoint retrieves a list of all available currency names and codes, along with their respective URLs, which contain their full data.")
    @GetMapping
    public ResponseEntity<?> getCurrencies() {
        List<Code> currencies = currencyService.getAllCurrencyCodes();
        return ResponseEntity.ok(currencies);
    }
}
