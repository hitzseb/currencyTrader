package com.hitzseb.currencyTrader.repository;

import com.hitzseb.currencyTrader.dto.CodeDto;
import com.hitzseb.currencyTrader.model.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {
    Optional<Market> findMarketByCode(String code);

    List<CodeDto> findNameAndCodeBy();
}
