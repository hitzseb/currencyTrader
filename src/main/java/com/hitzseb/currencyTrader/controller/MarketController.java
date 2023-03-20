package com.hitzseb.currencyTrader.controller;

import com.hitzseb.currencyTrader.model.Market;
import com.hitzseb.currencyTrader.service.CurrencyService;
import com.hitzseb.currencyTrader.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/market")
public class MarketController {
    @Autowired
    MarketService marketService;
    @Autowired
    CurrencyService currencyService;

    @GetMapping
    public String showAllMarkets(Model model) {
        model.addAttribute("markets", marketService.getAllMarkets());
        return "market_list";
    }

    @GetMapping("/save")
    public String showNewMarket(Model model) {
        Market market = new Market();
        model.addAttribute("currencies", currencyService.getAllCurrencies());
        model.addAttribute("market", market);
        return "market_new";
    }

    @PostMapping("/save")
    public String newMarket(@ModelAttribute Market market) {
        if (!marketService.marketCodeExists(market.getCode())) {
            marketService.saveMarket(market);
            return "redirect:/admin/market";
        } else {
            return "market_new";
        }

    }

    @GetMapping("/{id}/edit")
    public String showEditMarket(@PathVariable Long id, Model model) {
        try {
            Market market = marketService.getMarketById(id).get();
            model.addAttribute("currencies", currencyService.getAllCurrencies());
            model.addAttribute("market", market);
            return "market_edit";
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/{id}/edit")
    public String updateMarket(@PathVariable Long id, @ModelAttribute Market market, Model model) {
        boolean codeExists = marketService.marketCodeExists(market.getCode(), id);
        if (codeExists) {
            return "market_edit";
        }
        marketService.saveMarket(market);
        return "redirect:/admin/market";
    }

    @GetMapping("/{id}/delete")
    public String deleteMarket(@PathVariable Long id) {
        marketService.deleteMarketById(id);
        return "redirect:/admin/market";
    }
}
