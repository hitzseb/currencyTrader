package com.hitzseb.currencyTrader.service;

import com.hitzseb.currencyTrader.dto.CurrencyDto;
import com.hitzseb.currencyTrader.dto.MarketDto;
import com.hitzseb.currencyTrader.enums.Operation;
import com.hitzseb.currencyTrader.model.Currency;
import com.hitzseb.currencyTrader.model.Market;
import com.hitzseb.currencyTrader.response.ExchangeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExchangeService {
    @Autowired
    CurrencyService currencyService;
    @Autowired
    MarketService marketService;
    @Autowired
    CurrencyValueService currencyValueService;

    public ExchangeResponse exchangeCurrency
            (Operation operation, String marketCode, String currencyFromCode, String currencyToCode, double amount) {
        Market market = marketService.getMarketByCode(marketCode).get();
        Currency marketCurrency = market.getCurrency();
        String marketCurrencyName = marketCurrency.getName();
        String marketCurrencyCode = marketCurrency.getCode();
        String marketCurrencySymbol = marketCurrency.getSymbol();
        CurrencyDto marketCurrencyDto = new CurrencyDto(marketCurrencyName, marketCurrencyCode, marketCurrencySymbol);
        MarketDto marketDto = new MarketDto(market.getName(), marketCode, marketCurrencyDto);

        Currency currencyFrom = currencyService.getCurrencyByCode(currencyFromCode).get();
        Currency currencyTo = currencyService.getCurrencyByCode(currencyToCode).get();

        double currencyFromValue = 1;

        if (marketCurrency != currencyFrom) {
            if (operation == Operation.PURCHASE) {
                currencyFromValue = currencyValueService.getActiveValueOfCurrencyInMarket(currencyFrom, market)
                        .getPurchaseValue();
            } else if (operation == Operation.SALE) {
                currencyFromValue = currencyValueService.getActiveValueOfCurrencyInMarket(currencyFrom, market)
                        .getSaleValue();
            }
        }

        CurrencyDto currencyFromDto = new CurrencyDto(currencyFrom.getName(),currencyFromCode, currencyFrom.getSymbol());

        double convertedCurrencyFrom = amount * currencyFromValue;

        double currencyToValue = 1;

        if (marketCurrency != currencyTo) {
            if (operation == Operation.PURCHASE) {
                currencyToValue = currencyValueService.getActiveValueOfCurrencyInMarket(currencyTo, market)
                        .getPurchaseValue();
            } else if (operation == Operation.SALE) {
                currencyToValue = currencyValueService.getActiveValueOfCurrencyInMarket(currencyTo, market)
                        .getSaleValue();
            }
        }

        CurrencyDto currencyToDto = new CurrencyDto(currencyTo.getName(), currencyToCode, currencyTo.getSymbol());

        double convertedCurrencyTo = convertedCurrencyFrom / currencyToValue;

        ExchangeResponse exchangeResponse = new ExchangeResponse(operation.name(), marketDto,
                currencyFromDto, currencyToDto, currencyFromValue, currencyToValue,
                currencyFromCode + currencyFrom.getSymbol() + amount,
                currencyToCode + currencyTo.getSymbol() + convertedCurrencyTo);

        return exchangeResponse;
    }
}
