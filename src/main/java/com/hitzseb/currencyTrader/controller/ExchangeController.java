package com.hitzseb.currencyTrader.controller;

import com.hitzseb.currencyTrader.response.ExchangeResponse;
import com.hitzseb.currencyTrader.service.ExchangeService;
import com.hitzseb.currencyTrader.enums.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ExchangeController {
    @Autowired
    ExchangeService exchangeService;

    @io.swagger.v3.oas.annotations.Operation(summary = "This API endpoint converts an amount of one currency to another based on the latest market values and returns the equivalent value in the target currency.",
            description = "The `exchangeCurrency` API endpoint converts an amount of one currency to another based on the latest market values and returns the equivalent value in the target currency. The endpoint takes in five query parameters: `operation`, `market`, `from`, `to`, and `amount`. The `operation` parameter specifies the type of operation to be performed, which could be either \"buy\" or \"sell\". The `market` parameter specifies the market code where the conversion needs to be performed. The `from` parameter specifies the ISO code of the currency to be converted. The `to` parameter specifies the ISO code of the target currency. The `amount` parameter specifies the amount of the from currency to be converted.\n" +
                    "\n" +
                    "If any of the query parameters are missing, the API will return a bad request response with an appropriate message. If the API call is successful, it will return a JSON response containing the equivalent value in the target currency based on the latest market values.\n" +
                    "\n" +
                    "Please note that this API endpoint may throw an exception if any of the parameters are invalid or if there is an error while processing the request. In such cases, the API will return a bad request response with an appropriate message.")
    @GetMapping("/exchange")
    public ResponseEntity<?> exchangeCurrency(
            @Parameter(description = "Operation type")
            @RequestParam("operation") Optional<Operation> operation,
            @Parameter(description = "Market code; e.g. ARG")
            @RequestParam("market") Optional<String> marketCode,
            @Parameter(description = "Original currency iso code")
            @RequestParam("from") Optional<String> currencyFromCode,
            @Parameter(description = "target currency iso code")
            @RequestParam("to") Optional<String> currencyToCode,
            @Parameter(description = "Amount to exchange")
            @RequestParam("amount") Optional<Double> amount) {
        if (!(marketCode.isPresent() && currencyFromCode.isPresent() && currencyToCode.isPresent() && amount.isPresent())) {
            return ResponseEntity.badRequest().body(Constants.EMPTY_PARAM);
        }
        try {
            ExchangeResponse exchangeResponse = exchangeService.exchangeCurrency(operation.get(), marketCode.get(),
                    currencyFromCode.get(), currencyToCode.get(), amount.get());
            return ResponseEntity.ok(exchangeResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Constants.INVALID_PARAM);
        }
    }
}
