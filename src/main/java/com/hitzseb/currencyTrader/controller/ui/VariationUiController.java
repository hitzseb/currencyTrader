package com.hitzseb.currencyTrader.controller.ui;

import com.hitzseb.currencyTrader.service.CurrencyService;
import com.hitzseb.currencyTrader.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class VariationUiController {
    private final CurrencyService currencyService;
    private final MarketService marketService;

    @GetMapping("/variation")
    public String showVariation(Model model) {
        model.addAttribute("currencies", currencyService.getAllCurrencies());
        model.addAttribute("markets", marketService.getAllMarkets());
        return "variation";
    }
}
