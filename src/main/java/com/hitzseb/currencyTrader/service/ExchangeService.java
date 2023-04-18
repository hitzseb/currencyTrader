package com.hitzseb.currencyTrader.service;

import com.hitzseb.currencyTrader.dto.CurrencyDto;
import com.hitzseb.currencyTrader.dto.MarketDto;
import com.hitzseb.currencyTrader.enums.Operation;
import com.hitzseb.currencyTrader.model.Currency;
import com.hitzseb.currencyTrader.model.Market;
import com.hitzseb.currencyTrader.response.ExchangeResponse;
import com.hitzseb.currencyTrader.util.EntityUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final CurrencyService currencyService;
    private final MarketService marketService;
    private final CurrencyValueService currencyValueService;

    public ExchangeResponse exchangeCurrency(
            Operation operation,
            String marketCode,
            String currencyFromCode,
            String currencyToCode,
            double amount)
            throws EntityNotFoundException {
        Market market = EntityUtil.getEntityOrThrow(marketService.getMarketByCode(marketCode), "Market");
        Currency marketCurrency = market.getCurrency();
        String marketCurrencyName = marketCurrency.getName();
        String marketCurrencyCode = marketCurrency.getCode();
        String marketCurrencySymbol = marketCurrency.getSymbol();
        CurrencyDto marketCurrencyDto = new CurrencyDto(
                marketCurrencyName,
                marketCurrencyCode,
                marketCurrencySymbol);
        MarketDto marketDto = new MarketDto(
                market.getName(),
                marketCode,
                marketCurrencyDto);

        Currency currencyFrom = EntityUtil.getEntityOrThrow(currencyService.getCurrencyByCode(currencyFromCode), "Currency");

        Currency currencyTo = EntityUtil.getEntityOrThrow(currencyService.getCurrencyByCode(currencyToCode), "Currency");

        double currencyFromValue = getCurrencyValue(operation, currencyFrom, market);

        CurrencyDto currencyFromDto = new CurrencyDto(
                currencyFrom.getName(),
                currencyFromCode,
                currencyFrom.getSymbol());

        double convertedCurrencyFrom = amount * currencyFromValue;

        double currencyToValue = getCurrencyValue(operation, currencyTo, market);

        CurrencyDto currencyToDto = new CurrencyDto(
                currencyTo.getName(),
                currencyToCode,
                currencyTo.getSymbol());

        double convertedCurrencyTo = convertedCurrencyFrom / currencyToValue;

        return new ExchangeResponse(
                operation.name(),
                marketDto,
                currencyFromDto,
                currencyToDto,
                currencyFromValue,
                currencyToValue,
                currencyFromCode + currencyFrom.getSymbol() + amount,
                currencyToCode + currencyTo.getSymbol() + convertedCurrencyTo);
    }

    private double getCurrencyValue(Operation operation, Currency currency, Market market) {
        if (market.getCurrency() == currency) {
            return 1;
        }

        if (operation == Operation.PURCHASE) {
            return currencyValueService.getActiveValueOfCurrencyInMarket(currency, market)
                    .getPurchaseValue();
        } else if (operation == Operation.SALE) {
            return currencyValueService.getActiveValueOfCurrencyInMarket(currency, market)
                    .getSaleValue();
        }

        return 1;
    }
}
