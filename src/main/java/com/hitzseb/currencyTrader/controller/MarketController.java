package com.hitzseb.currencyTrader.controller;

import com.hitzseb.currencyTrader.model.Market;
import com.hitzseb.currencyTrader.service.MarketService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/market")
@RequiredArgsConstructor
public class MarketController {
    private final MarketService service;

    @Operation(summary = "Retrieves all markets in the system.",
            description = "This endpoint returns a list of all Market objects currently saved in the system.")
    @GetMapping
    public List<Market> getAllMarkets() {
        return service.getAllMarkets();
    }

    @Operation(summary = "Saves a new market in the system.",
            description = "This endpoint accepts a JSON payload containing the details of a new Market object" +
                    " to be saved in the system. If successful, it returns the saved Market object.")
    @PostMapping("/admin/save")
    public ResponseEntity<?> saveMarket(@RequestBody Market market) {
        try {
            Market marketResponse = service.saveMarket(market);
            return ResponseEntity.ok(marketResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Edits an existing market in the system.",
            description = "This endpoint updates the details of an existing Market object in the system," +
                    " identified by the ID provided in the URL path. It accepts optional query parameters" +
                    " for name and code to be updated, and a JSON payload containing the currency code to be updated." +
                    " If successful, it returns the updated Market object.")
    @PutMapping("/admin/{id}/edit")
    public ResponseEntity<?> editMarket(@PathVariable Long id,
                                        @RequestParam Optional<String> name,
                                        @RequestParam Optional<String> code,
                                        @RequestBody Optional<String> currencyCode) {
        try {
            Market market = service.editMarket(id, name, code, currencyCode);
            return ResponseEntity.ok(market);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Deletes an existing market from the system.",
            description = "This endpoint deletes an existing Market object from the system," +
                    " identified by the ID provided in the URL path. If successful, it returns a success message." +
                    " If the market is not found, it returns a 404 error message.")
    @DeleteMapping("/admin/{id}/delete")
    public ResponseEntity<?> deleteMarket(@PathVariable Long id) {
        try {
            service.deleteMarketById(id);
            return ResponseEntity.ok("Successfully deleted market with id: " + id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
