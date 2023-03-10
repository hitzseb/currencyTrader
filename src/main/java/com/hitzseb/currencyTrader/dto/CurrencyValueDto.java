package com.hitzseb.currencyTrader.dto;

import java.time.LocalDate;

public record CurrencyValueDto(LocalDate registeredAt, double saleValue) {
}
