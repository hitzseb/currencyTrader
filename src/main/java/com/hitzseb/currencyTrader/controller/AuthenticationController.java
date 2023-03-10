package com.hitzseb.currencyTrader.controller;

import com.hitzseb.currencyTrader.model.User;
import com.hitzseb.currencyTrader.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthenticationController {
    @Autowired
    private UserService userService;

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
}