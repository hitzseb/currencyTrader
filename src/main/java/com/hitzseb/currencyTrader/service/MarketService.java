package com.hitzseb.currencyTrader.service;

import com.hitzseb.currencyTrader.dto.CodeDto;
import com.hitzseb.currencyTrader.model.Market;
import com.hitzseb.currencyTrader.repository.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarketService {
    @Autowired
    MarketRepository marketRepository;

    public Optional<Market> getMarketByCode(String code) {
        return marketRepository.findMarketByCode(code);
    }

    public Optional<Market> getMarketById(Long id) {
        return  marketRepository.findById(id);
    }

    public List<Market> getAllMarkets() {
        return marketRepository.findAll();
    }
    public List<CodeDto> getAllMarketCodes() {
        return marketRepository.findNameAndCodeBy();
    }

    public boolean marketCodeExists(String code) {
        return marketCodeExists(code, null);
    }

    public boolean marketCodeExists(String code, Long id) {
        Market market = marketRepository.findMarketByCode(code).orElse(null);
        return ((market != null) && (id == null || !market.getId().equals(id)));
    }
    public void saveMarket(Market market) {
        marketRepository.save(market);
    }

    public void  deleteMarketById(Long id) {
        marketRepository.deleteById(id);
    }
}
