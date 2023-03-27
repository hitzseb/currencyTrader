package com.hitzseb.currencyTrader.service;

import com.hitzseb.currencyTrader.dto.CurrencyValueDto;
import com.hitzseb.currencyTrader.dto.RegisteredValueDto;
import com.hitzseb.currencyTrader.model.Currency;
import com.hitzseb.currencyTrader.model.CurrencyValue;
import com.hitzseb.currencyTrader.model.Market;
import com.hitzseb.currencyTrader.repository.CurrencyValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyValueService {
    private final CurrencyValueRepository currencyValueRepository;

    public Optional<CurrencyValue> getCurrencyValueById(Long id) {
        return  currencyValueRepository.findById(id);
    }

    public Page<CurrencyValue> getAllCurrencyValues(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return currencyValueRepository.findAll(pageable);
    }

    public void saveCurrencyValue(CurrencyValue currencyValue) {
        if (currencyValue.isActive()) {
            Optional<CurrencyValue> lastCurrencyValue = currencyValueRepository
                    .findByCurrencyAndMarketAndIsActiveIsTrue(
                            currencyValue.getCurrency(),
                            currencyValue.getMarket());
            if (lastCurrencyValue.isPresent()) {
                lastCurrencyValue.get().setActive(false);
            }
        }
        currencyValueRepository.save(currencyValue);
    }

    public void  deleteCurrencyValueById(Long id) {
        currencyValueRepository.deleteById(id);
    }

    public CurrencyValue getActiveValueOfCurrencyInMarket(Currency currency, Market market) {
        return currencyValueRepository.findByCurrencyAndMarketAndIsActiveIsTrue(currency, market).orElse(null);
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
            String saleValueStr = base.getCode() + base.getSymbol() + saleValue;
            return new RegisteredValueDto(registeredAtStr, saleValueStr);
        }).collect(Collectors.toList());
        return strValues;
    }
}
