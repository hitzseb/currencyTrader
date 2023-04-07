package com.hitzseb.currencyTrader.controller;

import com.hitzseb.currencyTrader.model.Currency;
import com.hitzseb.currencyTrader.model.CurrencyValue;
import com.hitzseb.currencyTrader.model.Market;
import com.hitzseb.currencyTrader.service.CurrencyService;
import com.hitzseb.currencyTrader.service.CurrencyValueService;
import com.hitzseb.currencyTrader.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUiController {
    private final CurrencyService currencyService;
    private final MarketService marketService;
    private final CurrencyValueService currencyValueService;

    //    CURRENCY

    @GetMapping("/currency")
    public String showAllCurrencies(Model model) {
        model.addAttribute("currencies", currencyService.getAllCurrencies());
        return "currency_list";
    }

    @GetMapping("/currency/save")
    public String showNewCurrency(Model model) {
        Currency currency = new Currency();
        model.addAttribute("currency", currency);
        return "currency_new";
    }

    @GetMapping("/currency/{id}/edit")
    public String showEditCurrency(@PathVariable Long id, Model model) {
        try {
            Currency currency = currencyService.getCurrencyById(id).get();
            model.addAttribute("currency", currency);
            return "currency_edit";
        } catch (Exception e) {
            return "error";
        }
    }

//    MARKET

    @GetMapping("/market")
    public String showAllMarkets(Model model) {
        model.addAttribute("markets", marketService.getAllMarkets());
        return "market_list";
    }

    @GetMapping("/market/save")
    public String showNewMarket(Model model) {
        Market market = new Market();
        model.addAttribute("currencies", currencyService.getAllCurrencies());
        model.addAttribute("market", market);
        return "market_new";
    }

    @GetMapping("/market/{id}/edit")
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

//    VALUE

    @GetMapping("/value")
    public String showAllValues(Model model) {
        model.addAttribute("currencies", currencyService.getAllCurrencies());
        model.addAttribute("markets", marketService.getAllMarkets());
        return "value_list";
    }

    @GetMapping("/value/save")
    public String showNewValue(Model model) {
        model.addAttribute("currencyValue", new CurrencyValue());
        model.addAttribute("currencies", currencyService.getAllCurrencies());
        model.addAttribute("markets", marketService.getAllMarkets());
        return "value_new";
    }

    @GetMapping("/value/{id}/edit")
    public String showEditValue(@PathVariable Long id, Model model) {
        try {
            CurrencyValue currencyValue = currencyValueService.getCurrencyValueById(id);
            model.addAttribute("currencies", currencyService.getAllCurrencies());
            model.addAttribute("markets", marketService.getAllMarkets());
            model.addAttribute("value", currencyValue);
            return "value_edit";
        } catch (Exception e) {
            return "error";
        }
    }
}
