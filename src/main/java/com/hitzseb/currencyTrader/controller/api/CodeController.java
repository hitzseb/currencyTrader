package com.hitzseb.currencyTrader.controller.api;

import com.hitzseb.currencyTrader.dto.CodeDto;
import com.hitzseb.currencyTrader.service.CodeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/code")
@RequiredArgsConstructor
public class CodeController {
    private final CodeService service;

    @Operation(summary = "Get all currency codes",
            description = "This endpoint retrieves a list of all available currency names and codes, along with their respective URLs, which contain their full data.")
    @GetMapping("/currency")
    public ResponseEntity<List<CodeDto>> getCurrencies() {
        List<CodeDto> currencies = service.getAllCurrencyCodes();
        return ResponseEntity.ok(currencies);
    }

    @Operation(summary = "Get all Market codes",
            description = "This endpoint retrieves a list of all available market names and codes, along with their respective URLs, which contain their full data.")
    @GetMapping("/market")
    public ResponseEntity<List<CodeDto>> getMarkets() {
        List<CodeDto> markets = service.getAllMarketCodes();
        return ResponseEntity.ok(markets);
    }
}
