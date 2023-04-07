package com.hitzseb.currencyTrader.service;

import com.hitzseb.currencyTrader.model.Currency;
import com.hitzseb.currencyTrader.repository.CurrencyRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public Currency saveCurrency(Currency currency) throws IllegalArgumentException {
        if (!(currency instanceof Currency)) {
            throw new IllegalArgumentException("The object currency must be an instance of the class Currency.");
        }
        return currencyRepository.save(currency);
    }

    public Currency editCurrency(Long id,
                             Optional<String> name,
                             Optional<String> code,
                             Optional<String> symbol)
            throws EntityNotFoundException {
        Currency currency = currencyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Currency not found with id: " + id));
        if (name.isPresent()) {
            currency.setName(name.get());
        }
        if (code.isPresent()) {
            currency.setCode(code.get());
        }
        if (symbol.isPresent()) {
            currency.setSymbol(symbol.get());
        }
        return currencyRepository.save(currency);
    }

    public void deleteCurrencyById(Long id) throws EntityNotFoundException {
        Currency currency;
        currency = currencyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Currency not found with id: " + id));
        currencyRepository.deleteById(id);
    }
}
