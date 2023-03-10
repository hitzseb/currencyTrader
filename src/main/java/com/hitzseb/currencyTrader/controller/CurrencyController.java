package com.hitzseb.currencyTrader.controller;

import com.hitzseb.currencyTrader.model.Currency;
import com.hitzseb.currencyTrader.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/currency")
//@RequestMapping("/admin/currency/")
public class CurrencyController {
    @Autowired
    CurrencyService currencyService;

    @GetMapping("/all")
    public String showAllCurrencies(Model model) {
        model.addAttribute("currencies", currencyService.getAllCurrencies());
        return "currency_list";
    }

    @GetMapping("/save")
    public String showNewCurrency(Model model) {
        Currency currency = new Currency();
        model.addAttribute("currency", currency);
        return "currency_new";
    }

    @PostMapping("/save")
    public String newCurrency(@ModelAttribute Currency currency) {
        if (!currencyService.currencyCodeExists(currency.getCode())) {
            currencyService.saveCurrency(currency);
            return "redirect:/currency/all";
        } else {
            return "currency_new";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditCurrency(@PathVariable Long id, Model model) {
        try {
            Currency currency = currencyService.getCurrencyById(id).get();
            model.addAttribute("currency", currency);
            return "currency_edit";
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/{id}/edit")
    public String updateCurrency(@PathVariable Long id,@ModelAttribute Currency currency, Model model) {
        boolean codeExists = currencyService.currencyCodeExists(currency.getCode(), id);
        if (codeExists) {
            return "currency_edit";
        }
        currencyService.saveCurrency(currency);
        return "redirect:/currency/all";
    }

    @GetMapping("/{id}/delete")
    public String deleteCurrency(@PathVariable Long id) {
        currencyService.deleteCurrencyById(id);
        return "redirect:/currency/all";
    }
}
