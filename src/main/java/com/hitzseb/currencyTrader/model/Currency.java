package com.hitzseb.currencyTrader.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "CURRENCIES")
@Data
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    private String symbol;
}
