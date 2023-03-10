package com.hitzseb.currencyTrader.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "CURRENCY_VALUES")
@Data
public class CurrencyValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate registeredAt;
    private double purchaseValue;
    private double saleValue;
    private boolean isActive;
    @ManyToOne
    Currency currency;
    @ManyToOne
    Market market;
}
