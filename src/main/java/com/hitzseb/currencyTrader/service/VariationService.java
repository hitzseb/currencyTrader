package com.hitzseb.currencyTrader.service;

import com.hitzseb.currencyTrader.dto.CurrencyDto;
import com.hitzseb.currencyTrader.dto.MarketDto;
import com.hitzseb.currencyTrader.dto.RegisteredValueDto;
import com.hitzseb.currencyTrader.model.Currency;
import com.hitzseb.currencyTrader.model.CurrencyValue;
import com.hitzseb.currencyTrader.model.Market;
import com.hitzseb.currencyTrader.response.VariationResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VariationService {
    private final CurrencyService currencyService;
    private final MarketService marketService;
    private final CurrencyValueService currencyValueService;

    public VariationResponse getExchangeRateVariationResponse(
            String currencyCode,
            String marketCode,
            LocalDate registeredAt,
            int pageNumber,
            int pageSize)
            throws EntityNotFoundException {
        Currency currency;
        currency = currencyService.getCurrencyByCode(currencyCode)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Currency not found with code " + currencyCode));
        CurrencyDto currencyDto = new CurrencyDto(
                currency.getName(),
                currencyCode,
                currency.getSymbol());

        Market market;
        market = marketService.getMarketByCode(marketCode)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Market not found with id " + marketCode));
        Currency marketCurrency = market.getCurrency();
        String marketCurrencyName = marketCurrency.getName();
        String marketCurrencyCode = marketCurrency.getCode();
        String marketCurrencySymbol = marketCurrency.getSymbol();
        CurrencyDto marketCurrencyDto = new CurrencyDto(marketCurrencyName, marketCurrencyCode, marketCurrencySymbol);
        MarketDto marketDto = new MarketDto(market.getName(), marketCode, marketCurrencyDto);

        List<RegisteredValueDto> values = currencyValueService
                .getAllValueDtoOfCurrencyInMarketSinceDate(currency, market, registeredAt);
        Collections.reverse(values);

        if (pageNumber < 1) {
            pageNumber = 1;
        }

        int fromIndex = (pageNumber - 1) * pageSize;
        int toIndex = Math.min(pageNumber * pageSize, values.size());
        List<RegisteredValueDto> pagedValues = values.subList(fromIndex, toIndex);

        VariationResponse variation = new VariationResponse(currencyDto, marketDto,
                printVariation(calculateExchangeRateVariation(currency, market, registeredAt)), pagedValues);
        return variation;
    }

    public String printVariation(double variation) {
        if(variation > 0) {
            return "+" + variation + "%";
        } else {
            return variation + "%";
        }
    }

    public double calculateExchangeRateVariation(Currency currency, Market market, LocalDate registeredAt) {
        double variation = 0;
        if (currency != market.getCurrency()) {
            List<CurrencyValue> values = currencyValueService.getAllValueOfCurrencyInMarketSinceDate
                    (currency, market, registeredAt);
            if (values.size() > 1) {
                if (values.size() > 2) {
                    double sumVariation = 0;
                    for (int i = 0; i < values.size()-1; i++) {
                        double oldValue = values.get(i).getSaleValue();
                        double newValue = values.get(i+1).getSaleValue();
                        sumVariation += calculateVariation(oldValue, newValue);
                    }
                    if (values.size() > 2) {
                        variation = sumVariation / (values.size() - 1);
                    } else {
                        variation = sumVariation;
                    }
                } else {
                    variation = calculateVariation(values.get(0).getSaleValue(), values.get(1).getSaleValue());
                }
            }
        }
        return variation;
    }

    public double calculateVariation(double oldValue, double newValue) {
        return (newValue - oldValue) / oldValue * 100;
    }
}
