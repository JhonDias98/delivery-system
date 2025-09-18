package com.delivery.restaurants.domain.valueobject;

public record CacheStats(
    long hitCount,
    long missCount,
    long evictionCount,
    double hitRate
) {
    public CacheStats {
        if (hitCount < 0) {
            throw new IllegalArgumentException("Hit count cannot be negative");
        }
        if (missCount < 0) {
            throw new IllegalArgumentException("Miss count cannot be negative");
        }
        if (evictionCount < 0) {
            throw new IllegalArgumentException("Eviction count cannot be negative");
        }
        if (hitRate < 0.0 || hitRate > 1.0) {
            throw new IllegalArgumentException("Hit rate must be between 0.0 and 1.0");
        }
    }
}

