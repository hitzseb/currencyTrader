package com.hitzseb.currencyTrader.service;

import com.hitzseb.currencyTrader.dto.CurrencyDto;
import com.hitzseb.currencyTrader.dto.MarketDto;
import com.hitzseb.currencyTrader.model.Currency;
import com.hitzseb.currencyTrader.model.Market;
import com.hitzseb.currencyTrader.response.ValueResponse;
import com.hitzseb.currencyTrader.util.EntityUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentValueService {
    private final CurrencyService currencyService;
    private final MarketService marketService;
    private final CurrencyValueService currencyValueService;

    public ValueResponse getCurrentValue(
            String currencyCode,
            String marketCode)
            throws EntityNotFoundException {
        Currency currency = EntityUtil.getEntityOrThrow(currencyService.getCurrencyByCode(currencyCode), "Currency");
        Market market =  EntityUtil.getEntityOrThrow(marketService.getMarketByCode(marketCode), "Market");
        Currency marketCurrency = market.getCurrency();
        String marketCurrencyName = marketCurrency.getName();
        String marketCurrencyCode = marketCurrency.getCode();
        String marketCurrencySymbol = marketCurrency.getSymbol();
        CurrencyDto marketCurrencyDto = new CurrencyDto(
                marketCurrencyName,
                marketCurrencyCode,
                marketCurrencySymbol);
        double currencyValue;
        if (currency != marketCurrency) {
            currencyValue = currencyValueService.getActiveValueOfCurrencyInMarket(
                    currency,
                    market).getSaleValue();
        } else {
            currencyValue = 1;
        }

        CurrencyDto currencyDto = new CurrencyDto(
                currency.getName(),
                currencyCode,
                currency.getSymbol());
        MarketDto marketDto = new MarketDto(
                market.getName(),
                marketCode,
                marketCurrencyDto);
        return new ValueResponse(
                currencyDto,
                marketDto,
                marketCurrencyCode + marketCurrencySymbol + currencyValue);
    }
}
