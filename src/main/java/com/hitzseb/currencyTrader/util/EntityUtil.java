package com.hitzseb.currencyTrader.util;

import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

public class EntityUtil {
    public static  <T> T getEntityOrThrow(Optional<T> optional, String entityName) {
        return optional.orElseThrow(() -> new EntityNotFoundException(entityName + " not found"));
    }
}
