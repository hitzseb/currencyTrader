package com.hitzseb.currencyTrader.controller.ui;

import com.hitzseb.currencyTrader.service.CurrencyService;
import com.hitzseb.currencyTrader.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CurrentValueUiController {
    private final CurrencyService currencyService;
    private final MarketService marketService;

    @GetMapping("/current")
    public String showCurrentValue(Model model) {
        model.addAttribute("currencies", currencyService.getAllCurrencies());
        model.addAttribute("markets", marketService.getAllMarkets());
        return "current_value";
    }
}
