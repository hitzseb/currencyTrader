package com.hitzseb.currencyTrader.controller;

import com.hitzseb.currencyTrader.model.Currency;
import com.hitzseb.currencyTrader.model.CurrencyValue;
import com.hitzseb.currencyTrader.model.Market;
import com.hitzseb.currencyTrader.model.User;
import com.hitzseb.currencyTrader.service.CurrencyService;
import com.hitzseb.currencyTrader.service.CurrencyValueService;
import com.hitzseb.currencyTrader.service.MarketService;
import com.hitzseb.currencyTrader.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MvcController {
    private final UserService userService;
    private final CurrencyService currencyService;
    private final MarketService marketService;

//    HOME

    @GetMapping("/")
    public String showHome() {
        return "home";
    }

//    AUTH

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String signUp(@ModelAttribute User user, @RequestParam String confirmPassword, BindingResult bindingResult) {
        if (!user.getPassword().equals(confirmPassword)) {
            bindingResult.rejectValue("password", "error.password", "Passwords do not match");
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.signUpUser(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

//    EXCHANGE

@GetMapping("/exchange")
public String showExchange(Model model) {
    model.addAttribute("currencies", currencyService.getAllCurrencies());
    model.addAttribute("markets", marketService.getAllMarkets());
    return "exchange";
}

//    VARIATION

    @GetMapping("/variation")
    public String showVariation(Model model) {
        model.addAttribute("currencies", currencyService.getAllCurrencies());
        model.addAttribute("markets", marketService.getAllMarkets());
        return "variation";
    }

//    CURRENT VALUE

    @GetMapping("/current")
    public String showCurrentValue(Model model) {
        model.addAttribute("currencies", currencyService.getAllCurrencies());
        model.addAttribute("markets", marketService.getAllMarkets());
        return "current_value";
    }

}
