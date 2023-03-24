package com.hitzseb.currencyTrader.service;

import com.hitzseb.currencyTrader.dto.CodeDto;
import com.hitzseb.currencyTrader.repository.CurrencyRepository;
import com.hitzseb.currencyTrader.repository.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeService {
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    MarketRepository marketRepository;

    public List<CodeDto> getAllCurrencyCodes() {
        return currencyRepository.findNameAndCodeBy();
    }

    public List<CodeDto> getAllMarketCodes() {
        return marketRepository.findNameAndCodeBy();
    }
}
