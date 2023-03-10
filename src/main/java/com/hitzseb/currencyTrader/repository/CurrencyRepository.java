package com.hitzseb.currencyTrader.repository;

import com.hitzseb.currencyTrader.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    public Optional<Currency> findCurrencyByCode(String code);
}
