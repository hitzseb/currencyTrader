package com.hitzseb.currencyTrader.service;

import com.hitzseb.currencyTrader.model.Currency;
import com.hitzseb.currencyTrader.model.Market;
import com.hitzseb.currencyTrader.repository.MarketRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarketService {
    private final MarketRepository marketRepository;
    private final CurrencyService currencyService;

    public Optional<Market> getMarketByCode(String code) {
        return marketRepository.findMarketByCode(code);
    }

    public Optional<Market> getMarketById(Long id) {
        return  marketRepository.findById(id);
    }

    public List<Market> getAllMarkets() {
        return marketRepository.findAll();
    }

    public Market saveMarket(Market market) throws IllegalArgumentException {
        if (market == null) {
            throw new IllegalArgumentException("The object market cant be null.");
        }
        return marketRepository.save(market);
    }

    public Market editMarket(Long id,
                             Optional<String> name,
                             Optional<String> code,
                             Optional<String> currencyCode)
            throws EntityNotFoundException {
        Market market = marketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Market not found with id: " + id));
        name.ifPresent(market::setName);
        code.ifPresent(market::setCode);
        if (currencyCode.isPresent()) {
            Currency currency = currencyService.getCurrencyByCode(currencyCode.get())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Currency not found with code: " + currencyCode));
            market.setCurrency(currency);
        }
        return marketRepository.save(market);
    }

    public void  deleteMarketById(Long id) throws EntityNotFoundException {
        marketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Market not found with id: " + id));
        marketRepository.deleteById(id);
    }
}
