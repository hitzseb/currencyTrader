package com.hitzseb.currencyTrader.service;

import com.hitzseb.currencyTrader.dto.CurrencyValueDto;
import com.hitzseb.currencyTrader.dto.RegisteredValueDto;
import com.hitzseb.currencyTrader.model.Currency;
import com.hitzseb.currencyTrader.model.CurrencyValue;
import com.hitzseb.currencyTrader.model.Market;
import com.hitzseb.currencyTrader.repository.CurrencyValueRepository;
import com.hitzseb.currencyTrader.util.EntityUtil;
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
        Currency currency = EntityUtil.getEntityOrThrow(currencyService.getCurrencyByCode(currencyCode), "Currency");
        Market market = EntityUtil.getEntityOrThrow(marketService.getMarketByCode(marketCode), "Market");
        Pageable pageable = PageRequest.of(page, size);
        return currencyValueRepository.findByCurrencyAndMarket(pageable, currency, market);
    }

    public CurrencyValue getCurrencyValueById(Long id) throws EntityNotFoundException {
        CurrencyValue value = EntityUtil.getEntityOrThrow(currencyValueRepository.findById(id), "CurrencyValue");
        return value;
    }

    public CurrencyValue saveCurrencyValue(CurrencyValue currencyValue) throws IllegalArgumentException {
        if (currencyValue == null) {
            throw new IllegalArgumentException("The object currencyValue cant be null.");
        }
        if (currencyValue.isActive()) {
            Optional<CurrencyValue> lastCurrencyValue = currencyValueRepository
                    .findByCurrencyAndMarketAndIsActiveIsTrue(
                            currencyValue.getCurrency(),
                            currencyValue.getMarket());
            lastCurrencyValue.ifPresent(value -> value.setActive(false));
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
        CurrencyValue value = EntityUtil.getEntityOrThrow(currencyValueRepository.findById(id), "CurrencyValue");
        registeredAt.ifPresent(value::setRegisteredAt);
        purchaseValue.ifPresent(value::setPurchaseValue);
        saleValue.ifPresent(value::setSaleValue);
        isActive.ifPresent(value::setActive);
        return currencyValueRepository.save(value);
    }

    public void deleteCurrencyValueById(Long id) throws EntityNotFoundException {
        CurrencyValue value = EntityUtil.getEntityOrThrow(currencyValueRepository.findById(id), "CurrencyValue");
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

    public Page<RegisteredValueDto> getAllValueDtoOfCurrencyInMarketSinceDate(
            Pageable pageable, Currency currency, Market market, LocalDate registeredAt) {

        Page<CurrencyValueDto> rawValues = currencyValueRepository
                .findRegisteredAtAndSaleValueByCurrencyAndMarketAndRegisteredAtAfterOrderByRegisteredAtDesc(pageable, currency, market, registeredAt);

        return rawValues.map(currencyValueDto -> {
            Currency base = market.getCurrency();
            LocalDate date = currencyValueDto.registeredAt();
            double saleValue = currencyValueDto.saleValue();
            String registeredAtStr = date.toString();
            String saleValueStr = base.getCode() + base.getSymbol() + saleValue;
            return new RegisteredValueDto(registeredAtStr, saleValueStr);
        });
    }

}
