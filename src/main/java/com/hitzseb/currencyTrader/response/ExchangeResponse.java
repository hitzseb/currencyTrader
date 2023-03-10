package com.hitzseb.currencyTrader.response;

import com.hitzseb.currencyTrader.dto.CurrencyDto;
import com.hitzseb.currencyTrader.dto.MarketDto;

public record ExchangeResponse(String operation, MarketDto market,
                               CurrencyDto currencyFrom, CurrencyDto currencyTo,
                               double currencyFromMarketValue, double currencyToMarketValue,
                               String inputAmount, String outputAmount) {
}
