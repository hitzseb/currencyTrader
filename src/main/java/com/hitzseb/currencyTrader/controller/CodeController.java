package com.hitzseb.currencyTrader.controller;

import com.hitzseb.currencyTrader.dto.CodeDto;
import com.hitzseb.currencyTrader.service.CurrencyService;
import com.hitzseb.currencyTrader.service.MarketService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/code")
public class CodeController {
    @Autowired
    CurrencyService currencyService;
    @Autowired
    MarketService marketService;

    @Operation(summary = "Get all currency codes",
            description = "This endpoint retrieves a list of all available currency names and codes, along with their respective URLs, which contain their full data.")
    @GetMapping("/currency")
    public ResponseEntity<?> getCurrencies() {
        List<CodeDto> currencies = currencyService.getAllCurrencyCodes();
        return ResponseEntity.ok(currencies);
    }

    @Operation(summary = "Get all Market codes",
            description = "This endpoint retrieves a list of all available market names and codes, along with their respective URLs, which contain their full data.")
    @GetMapping("/market")
    public ResponseEntity<?> getMarkets() {
        List<CodeDto> markets = marketService.getAllMarketCodes();
        return ResponseEntity.ok(markets);
    }
}
