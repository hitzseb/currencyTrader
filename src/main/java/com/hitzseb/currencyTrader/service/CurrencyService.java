package com.hitzseb.currencyTrader.service;

import com.hitzseb.currencyTrader.model.Currency;
import com.hitzseb.currencyTrader.repository.CurrencyRepository;
import com.hitzseb.currencyTrader.util.EntityUtil;
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

    public Currency saveCurrency(Currency currency) throws IllegalArgumentException {
        if (currency == null) {
            throw new IllegalArgumentException("The object currency cant be null.");
        }
        return currencyRepository.save(currency);
    }

    public Currency editCurrency(Long id,
                             Optional<String> name,
                             Optional<String> code,
                             Optional<String> symbol)
            throws EntityNotFoundException {
        Currency currency = EntityUtil.getEntityOrThrow(currencyRepository.findById(id), "Currency");
        name.ifPresent(currency::setName);
        code.ifPresent(currency::setCode);
        symbol.ifPresent(currency::setSymbol);
        return currencyRepository.save(currency);
    }

    public void deleteCurrencyById(Long id) throws EntityNotFoundException {
        EntityUtil.getEntityOrThrow(currencyRepository.findById(id), "Currency");
        currencyRepository.deleteById(id);
    }
}
