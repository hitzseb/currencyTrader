package com.hitzseb.currencyTrader.controller.ui;

import com.hitzseb.currencyTrader.model.CurrencyValue;
import com.hitzseb.currencyTrader.service.CurrencyService;
import com.hitzseb.currencyTrader.service.CurrencyValueService;
import com.hitzseb.currencyTrader.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/value")
@RequiredArgsConstructor
public class CurrencyValueController {
    private final CurrencyValueService currencyValueService;
    private final CurrencyService currencyService;
    private final MarketService marketService;

    @GetMapping
    public String showAllValues(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CurrencyValue> currencyValues = currencyValueService.getAllCurrencyValues(page, size);
        model.addAttribute("values", currencyValues.getContent());
        model.addAttribute("page", currencyValues);
        model.addAttribute("url", "/all");
        return "value_list";
    }

    @GetMapping("/save")
    public String showNewValue(Model model) {
        model.addAttribute("currencyValue", new CurrencyValue());
        model.addAttribute("currencies", currencyService.getAllCurrencies());
        model.addAttribute("markets", marketService.getAllMarkets());
        return "value_new";
    }

    @PostMapping("/save")
    public String newValue(@ModelAttribute CurrencyValue currencyValue) {
        currencyValueService.saveCurrencyValue(currencyValue);
        return "redirect:/admin/value";
    }

    @GetMapping("/{id}/edit")
    public String showEditValue(@PathVariable Long id, Model model) {
        try {
            CurrencyValue currencyValue = currencyValueService.getCurrencyValueById(id).get();
            model.addAttribute("currencies", currencyService.getAllCurrencies());
            model.addAttribute("markets", marketService.getAllMarkets());
            model.addAttribute("value", currencyValue);
            return "value_edit";
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/{id}/save")
    public String updateValue(@PathVariable Long id, @ModelAttribute CurrencyValue currencyValue, Model model) {
        currencyValueService.saveCurrencyValue(currencyValue);
        return "redirect:/admin/value";
    }

    @GetMapping("/{id}/delete")
    public String deleteValue(@PathVariable Long id) {
        currencyValueService.deleteCurrencyValueById(id);
        return "redirect:/admin/value";
    }
}