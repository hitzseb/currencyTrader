package com.hitzseb.currencyTrader.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "MARKETS")
@Data
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    @ManyToOne
    private Currency currency;
}
