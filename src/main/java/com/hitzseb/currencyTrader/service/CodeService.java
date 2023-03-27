package com.hitzseb.currencyTrader.service;

import com.hitzseb.currencyTrader.dto.CodeDto;
import com.hitzseb.currencyTrader.repository.CurrencyRepository;
import com.hitzseb.currencyTrader.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeService {
    private final CurrencyRepository currencyRepository;
    private final MarketRepository marketRepository;

    public List<CodeDto> getAllCurrencyCodes() {
        return currencyRepository.findNameAndCodeBy();
    }

    public List<CodeDto> getAllMarketCodes() {
        return marketRepository.findNameAndCodeBy();
    }
}
