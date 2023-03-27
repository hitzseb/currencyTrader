package com.hitzseb.currencyTrader.service;

import com.hitzseb.currencyTrader.model.Currency;
import com.hitzseb.currencyTrader.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public Optional<Currency> getCurrencyByCode(String code) {
        return currencyRepository.findCurrencyByCode(code);
    }

    public Optional<Currency> getCurrencyById(Long id) {
        return currencyRepository.findById(id);
    }

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    public boolean currencyCodeExists(String code) {
        return currencyCodeExists(code, null);
    }

    public boolean currencyCodeExists(String code, Long id) {
        Currency currency = currencyRepository.findCurrencyByCode(code).orElse(null);
        return ((currency != null) && (id == null || !currency.getId().equals(id)));
    }

    public void saveCurrency(Currency currency) {
        currencyRepository.save(currency);
    }

    public void deleteCurrencyById(Long id) {
        currencyRepository.deleteById(id);
    }
}
