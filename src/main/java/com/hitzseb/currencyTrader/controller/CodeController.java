package com.hitzseb.currencyTrader.controller;

import com.hitzseb.currencyTrader.model.Currency;
import com.hitzseb.currencyTrader.model.Market;
import com.hitzseb.currencyTrader.service.CurrencyService;
import com.hitzseb.currencyTrader.service.MarketService;
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

    @GetMapping("/currency")
    public ResponseEntity<List<Currency>> getCurrencies() {
        List<Currency> currencies = currencyService.getAllCurrencies();
        return ResponseEntity.ok(currencies);
    }

    @GetMapping("/market")
    public ResponseEntity<List<Market>> getMarkets() {
        List<Market> markets = marketService.getAllMarkets();
        return ResponseEntity.ok(markets);
    }
}
