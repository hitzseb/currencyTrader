package com.hitzseb.currencyTrader.controller;

import com.hitzseb.currencyTrader.model.CurrencyValue;
import com.hitzseb.currencyTrader.service.CurrencyValueService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/value")
@RequiredArgsConstructor
public class CurrencyValueController {
    private final CurrencyValueService service;

    @Operation(summary = "Retrieves all currency values in the system.",
            description = "Retrieves a page of CurrencyValue objects matching the provided" +
                    " currencyCode and marketCode query parameters. The results can be paginated" +
                    " using the page and size parameters, which default to 0 and 10, respectively." +
                    " Returns an HTTP 400 Bad Request if either the currencyCode or marketCode" +
                    " parameters are missing. Returns an HTTP 404 Not Found if no CurrencyValue" +
                    " objects are found for the provided parameters.")
    @GetMapping
    public ResponseEntity<?> getAllValues(@RequestParam("currency") Optional<String> currencyCode,
                                          @RequestParam("market") Optional<String> marketCode,
                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                          @RequestParam(value = "size", defaultValue = "10") int size) {
        if (!currencyCode.isPresent()) {
            return ResponseEntity.badRequest().body("Parameter currency is missing.");
        }
        if (!marketCode.isPresent()) {
            return ResponseEntity.badRequest().body("Parameter market is missing.");
        }
        try {
            Page<CurrencyValue> response = service.getAllCurrencyValues(page, size, currencyCode.get(), marketCode.get());
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Saves a new currency value.",
            description = "Creates a new CurrencyValue object with the provided request body." +
                    " Returns the newly created object on success. Returns an HTTP 400 Bad Request" +
                    " if the request body is missing any required fields or contains invalid data.")
    @PostMapping("/admin/save")
    public ResponseEntity<?> saveCurrencyValue(@RequestBody CurrencyValue currencyValue) {
        try {
            CurrencyValue value = service.saveCurrencyValue(currencyValue);
            return ResponseEntity.ok(value);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Updates an existing currency value.",
            description = "Updates an existing CurrencyValue object with the provided id." +
                    " Accepts optional query parameters registeredAt, purchaseValue," +
                    " saleValue, and isActive to update specific fields of the object." +
                    " Returns the updated object on success. Returns an HTTP 404 Not Found" +
                    " if no CurrencyValue object is found for the provided id.")
    @PutMapping("/admin/{id}/edit")
    public ResponseEntity<?> editCurrencyValue(@PathVariable Long id,
                                               @RequestParam Optional<LocalDate> registeredAt,
                                               @RequestParam Optional<Double> purchaseValue,
                                               @RequestParam Optional<Double> saleValue,
                                               @RequestParam Optional<Boolean> isActive) {
        try {
            CurrencyValue value = service.editCurrencyValue(id, registeredAt, purchaseValue, saleValue, isActive);
            return ResponseEntity.ok(value);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Deletes an existing currency value.",
            description = "Deletes an existing CurrencyValue object with the provided id." +
                    " Returns a success message on success. Returns an HTTP 404 Not Found" +
                    " if no CurrencyValue object is found for the provided id.")
    @DeleteMapping("/admin/{id}/delete")
    public ResponseEntity<?> deleteCurrencyValue(@PathVariable Long id) {
        try {
            service.deleteCurrencyValueById(id);
            return ResponseEntity.ok("Successfully deleted currency value with id: " + id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
