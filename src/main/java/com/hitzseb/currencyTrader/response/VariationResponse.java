package com.hitzseb.currencyTrader.response;

import com.hitzseb.currencyTrader.dto.CurrencyDto;
import com.hitzseb.currencyTrader.dto.MarketDto;
import com.hitzseb.currencyTrader.dto.RegisteredValueDto;

import java.util.List;

public record VariationResponse(CurrencyDto currency, MarketDto market, String variation,
                                List<RegisteredValueDto> registeredValues) {
}
