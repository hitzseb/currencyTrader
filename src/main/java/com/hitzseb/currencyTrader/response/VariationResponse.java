package com.hitzseb.currencyTrader.response;

import com.hitzseb.currencyTrader.dto.CurrencyDto;
import com.hitzseb.currencyTrader.dto.MarketDto;
import com.hitzseb.currencyTrader.dto.RegisteredValueDto;
import org.springframework.data.domain.Page;

public record VariationResponse(CurrencyDto currency, MarketDto market, String variation,
                                Page<RegisteredValueDto> registeredValues) {
}
