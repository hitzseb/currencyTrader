package com.hitzseb.currencyTrader.repository;

import com.hitzseb.currencyTrader.dto.CurrencyValueDto;
import com.hitzseb.currencyTrader.model.Currency;
import com.hitzseb.currencyTrader.model.CurrencyValue;
import com.hitzseb.currencyTrader.model.Market;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyValueRepository extends JpaRepository<CurrencyValue, Long> {
    Page<CurrencyValue> findAll(Pageable pageable);
    Page<CurrencyValue> findByCurrencyAndMarket(Pageable pageable, Currency currency, Market market);
    Optional<CurrencyValue> findByCurrencyAndMarketAndIsActiveIsTrue(Currency currency, Market market);


    List<CurrencyValue> findByCurrencyAndMarketAndRegisteredAtAfterOrderByRegisteredAt
            (Currency currency, Market market, LocalDate registeredAt);

    Page<CurrencyValueDto> findRegisteredAtAndSaleValueByCurrencyAndMarketAndRegisteredAtAfterOrderByRegisteredAtDesc
            (Pageable pageable, Currency currency, Market market, LocalDate registeredAt);
}
