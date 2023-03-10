package com.hitzseb.currencyTrader.service;

import com.hitzseb.currencyTrader.dto.CurrencyDto;
import com.hitzseb.currencyTrader.dto.MarketDto;
import com.hitzseb.currencyTrader.dto.RegisteredValueDto;
import com.hitzseb.currencyTrader.model.Currency;
import com.hitzseb.currencyTrader.model.CurrencyValue;
import com.hitzseb.currencyTrader.model.Market;
import com.hitzseb.currencyTrader.response.VariationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class VariationService {
    @Autowired
    CurrencyService currencyService;
    @Autowired
    MarketService marketService;
    @Autowired
    CurrencyValueService currencyValueService;

    public VariationResponse getExchangeRateVariationResponse
            (String currencyCode, String marketCode, LocalDate registeredAt) {
        Currency currency = currencyService.getCurrencyByCode(currencyCode).get();
        CurrencyDto currencyDto = new CurrencyDto(currency.getName(), currencyCode, currency.getSymbol());
        Market market = marketService.getMarketByCode(marketCode).get();
        Currency marketCurrency = market.getCurrency();
        String marketCurrencyName = marketCurrency.getName();
        String marketCurrencyCode = marketCurrency.getCode();
        String marketCurrencySymbol = marketCurrency.getSymbol();
        CurrencyDto marketCurrencyDto = new CurrencyDto(marketCurrencyName, marketCurrencyCode, marketCurrencySymbol);
        MarketDto marketDto = new MarketDto(market.getName(), marketCode, marketCurrencyDto);
        List<RegisteredValueDto> values = currencyValueService
                .getAllValueDtoOfCurrencyInMarketSinceDate(currency, market, registeredAt);
        Collections.reverse(values);
        VariationResponse variation = new VariationResponse(currencyDto, marketDto,
                printVariation(calculateExchangeRateVariation(currency, market, registeredAt)), values);
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
                    for (int i = 0; i < values.size()-1; i++) {
                        double oldValue = values.get(i).getSaleValue();
                        double newValue = values.get(i+1).getSaleValue();
                        variation = calculateVariation(oldValue, newValue);
                    }
                    variation = variation / (values.size() - 1);
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
