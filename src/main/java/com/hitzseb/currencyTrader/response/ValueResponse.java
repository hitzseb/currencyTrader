package com.hitzseb.currencyTrader.response;

import com.hitzseb.currencyTrader.dto.CurrencyDto;
import com.hitzseb.currencyTrader.dto.MarketDto;

public record ValueResponse(CurrencyDto currency, MarketDto market, String value) {
}
