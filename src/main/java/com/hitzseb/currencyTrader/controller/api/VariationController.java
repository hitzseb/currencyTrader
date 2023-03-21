package com.hitzseb.currencyTrader.controller.api;

import com.hitzseb.currencyTrader.response.VariationResponse;
import com.hitzseb.currencyTrader.service.VariationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class VariationController {
    @Autowired
    VariationService variationService;

    @Operation(summary = "This API endpoint calculates the variation rate of an exchange rate for a specified currency in a market since a given date, and returns the associated value records.",
            description = "The `exchangeRateVariation` API endpoint calculates the variation rate of an exchange rate for a specified currency in a market since a given date. The endpoint takes in three query parameters: `currency`, `market`, and `date`. The `currency` parameter specifies the ISO code of the currency for which the exchange rate variation needs to be calculated. The `market` parameter specifies the market code where the exchange rate needs to be calculated. The `date` parameter specifies the date from which the exchange rate variation needs to be calculated in the format \"YYYY-MM-DD\".\n" +
                    "\n" +
                    "If any of the query parameters are missing, the API will return a bad request response with an appropriate message. If the API call is successful, it will return a JSON response containing the exchange rate variation rate and associated value records.\n" +
                    "\n" +
                    "Please note that this API endpoint may throw an exception if any of the parameters are invalid or if there is an error while processing the request. In such cases, the API will return a bad request response with an appropriate message.")
    @GetMapping("/variation")
    public ResponseEntity<?> exchangeRateVariation(
            @Parameter(description = "Currency iso code")
            @RequestParam("currency") Optional<String> currencyCode,
            @Parameter(description = "Market code: e.g. ARG")
            @RequestParam("market") Optional<String> marketCode,
            @Parameter(description = "YYYY-MM-DD format date")
            @RequestParam("date")Optional<LocalDate> registeredAt) {
        if (!(currencyCode.isPresent() && marketCode.isPresent() && registeredAt.isPresent())) {
            return ResponseEntity.badRequest().body(Constants.EMPTY_PARAM);
        }
        try {
            VariationResponse variation = variationService.getExchangeRateVariationResponse(currencyCode.get(),
                    marketCode.get(), registeredAt.get());
            return ResponseEntity.ok(variation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Constants.INVALID_PARAM);
        }
    }
}
