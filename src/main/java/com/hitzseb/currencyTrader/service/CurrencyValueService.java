package com.hitzseb.currencyTrader.service;

import com.hitzseb.currencyTrader.dto.CurrencyValueDto;
import com.hitzseb.currencyTrader.dto.RegisteredValueDto;
import com.hitzseb.currencyTrader.model.Currency;
import com.hitzseb.currencyTrader.model.CurrencyValue;
import com.hitzseb.currencyTrader.model.Market;
import com.hitzseb.currencyTrader.repository.CurrencyValueRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private final CurrencyService currencyService;
    private final MarketService marketService;
    private final CurrencyValueRepository currencyValueRepository;

    public Page<CurrencyValue> getAllCurrencyValues(
            int page,
            int size,
            String currencyCode,
            String marketCode)
            throws EntityNotFoundException {
        Currency currency = currencyService.getCurrencyByCode(currencyCode)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Currency not found with code: " + currencyCode));
        Market market = marketService.getMarketByCode(marketCode)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Market not found with code: " + marketCode));
        Pageable pageable = PageRequest.of(page, size);
        return currencyValueRepository.findByCurrencyAndMarket(pageable, currency, market);
    }

    public CurrencyValue getCurrencyValueById(Long id) throws EntityNotFoundException {
        CurrencyValue value;
        value = currencyValueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Currency value not found with id: " + id));
        return value;
    }

    public CurrencyValue saveCurrencyValue(CurrencyValue currencyValue) throws IllegalArgumentException {
        if (!(currencyValue instanceof CurrencyValue)) {
            throw new IllegalArgumentException("The object currencyValue must be an instance of the class CurrencyValue.");
        }
        if (currencyValue.isActive()) {
            Optional<CurrencyValue> lastCurrencyValue = currencyValueRepository
                    .findByCurrencyAndMarketAndIsActiveIsTrue(
                            currencyValue.getCurrency(),
                            currencyValue.getMarket());
            if (lastCurrencyValue.isPresent()) {
                lastCurrencyValue.get().setActive(false);
            }
        }
        return currencyValueRepository.save(currencyValue);
    }

    public CurrencyValue editCurrencyValue(
            Long id,
            Optional<LocalDate> registeredAt,
            Optional<Double> purchaseValue,
            Optional<Double> saleValue,
            Optional<Boolean> isActive)
            throws EntityNotFoundException {
        CurrencyValue value = currencyValueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Currency value not found with id: " + id));
        if (registeredAt.isPresent()) {
            value.setRegisteredAt(registeredAt.get());
        }
        if (purchaseValue.isPresent()) {
            value.setPurchaseValue(purchaseValue.get());
        }
        if (saleValue.isPresent()) {
            value.setSaleValue(saleValue.get());
        }
        if (isActive.isPresent()) {
            value.setActive(isActive.get());
        }
        return currencyValueRepository.save(value);
    }

    public void deleteCurrencyValueById(Long id) throws EntityNotFoundException {
        CurrencyValue value = currencyValueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Currency value not found with id: " + id));
        currencyValueRepository.delete(value);
    }

    public CurrencyValue getActiveValueOfCurrencyInMarket(Currency currency, Market market) {
        return currencyValueRepository.findByCurrencyAndMarketAndIsActiveIsTrue(currency, market).orElse(null);
    }

    public List<CurrencyValue> getAllValuesOfCurrencyInMarketSinceDate
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
