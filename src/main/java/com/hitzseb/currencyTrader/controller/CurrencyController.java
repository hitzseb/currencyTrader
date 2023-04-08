package com.hitzseb.currencyTrader.controller;

import com.hitzseb.currencyTrader.model.Currency;
import com.hitzseb.currencyTrader.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/currency")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService service;

    @Operation(summary = "Retrieves all currencies in the system.",
            description = "This endpoint returns a list of all Currency objects currently saved in the system.")
    @GetMapping
    public List<Currency> getAllCurrencies() {
        return service.getAllCurrencies();
    }

    @Operation(summary = "Saves a new currency in the system.",
            description = "This endpoint accepts a JSON payload containing the details of a new Currency object" +
                    " to be saved in the system. If successful, it returns the saved Currency object.")
    @PostMapping("/admin/save")
    public ResponseEntity<?> saveCurrency(@RequestBody Currency currency) {
        try {
            Currency currencyResponse = service.saveCurrency(currency);
            return ResponseEntity.ok(currencyResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Edits an existing currency in the system.",
            description = "This endpoint updates the details of an existing Currency object in the system," +
                    " identified by the ID provided in the URL path. It accepts optional query parameters for" +
                    " name, code, and symbol to be updated. If successful, it returns the updated Currency object.")
    @PutMapping("/admin/{id}/edit")
    public ResponseEntity<?> editMarket(@PathVariable Long id,
                                        @RequestParam Optional<String> name,
                                        @RequestParam Optional<String> code,
                                        @RequestParam Optional<String> symbol) {
        try {
            Currency currency = service.editCurrency(id, name, code, symbol);
            return ResponseEntity.ok(currency);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Deletes an existing currency from the system.",
            description = "This endpoint deletes an existing Currency object from the system," +
                    " identified by the ID provided in the URL path. If successful, it returns a success message." +
                    " If the currency is not found, it returns a 404 error message.")
    @DeleteMapping("/admin/{id}/delete")
    public ResponseEntity<?> deleteMarket(@PathVariable Long id) {
        try {
            service.deleteCurrencyById(id);
            return ResponseEntity.ok("Successfully deleted currency with id: " + id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
