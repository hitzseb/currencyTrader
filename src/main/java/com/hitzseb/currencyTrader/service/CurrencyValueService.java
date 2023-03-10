package com.hitzseb.currencyTrader.service;

import com.hitzseb.currencyTrader.dto.CurrencyValueDto;
import com.hitzseb.currencyTrader.dto.RegisteredValueDto;
import com.hitzseb.currencyTrader.model.Currency;
import com.hitzseb.currencyTrader.model.CurrencyValue;
import com.hitzseb.currencyTrader.model.Market;
import com.hitzseb.currencyTrader.repository.CurrencyValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CurrencyValueService {
    @Autowired
    CurrencyValueRepository currencyValueRepository;

    public Optional<CurrencyValue> getCurrencyValueById(Long id) {
        return  currencyValueRepository.findById(id);
    }

    public List<CurrencyValue> getAllCurrencyValues() {
        return currencyValueRepository.findAll();
    }

    public void saveCurrencyValue(CurrencyValue currencyValue) {
        currencyValueRepository.save(currencyValue);
    }

    public void  deleteCurrencyValueById(Long id) {
        currencyValueRepository.deleteById(id);
    }

    public CurrencyValue getActiveValueOfCurrencyInMarket(Currency currency, Market market) {
        return currencyValueRepository.findByCurrencyAndMarketAndIsActiveIsTrue(currency, market).get();
    }

    public List<CurrencyValue> getAllValueOfCurrencyInMarketSinceDate
            (Currency currency, Market market, LocalDate registeredAt) {
        return currencyValueRepository.findByCurrencyAndMarketAndRegisteredAtAfterOrderByRegisteredAt
                (currency, market, registeredAt);
    }

    public List<RegisteredValueDto> getAllValueDtoOfCurrencyInMarketSinceDate
            (Currency currency, Market market, LocalDate registeredAt) {
        List<CurrencyValueDto> rawValues = currencyValueRepository.findRegisteredAtAndSaleValueByCurrencyAndMarketAndRegisteredAtAfterOrderByRegisteredAt(
                currency, market, registeredAt);

        List<RegisteredValueDto> strValues = rawValues.stream().map(currencyValueDto -> {
            Currency base = market.getCurrency();
            LocalDate date = currencyValueDto.registeredAt();
            double saleValue = currencyValueDto.saleValue();
            String registeredAtStr = date.toString();
            String saleValueStr = base.getCode() + base.getSymbol() + String.valueOf(saleValue);
            return new RegisteredValueDto(registeredAtStr, saleValueStr);
        }).collect(Collectors.toList());
        return strValues;
    }
}
