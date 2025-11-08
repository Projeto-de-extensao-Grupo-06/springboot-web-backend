package com.solarize.solarizeWebBackend.shared.cacheManager.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

public abstract class CaffeineManager<K, V> {
    final int durationMinutes;
    private final Cache<K, V> cache;

    public CaffeineManager(int durationMinutes) {
        this.durationMinutes = durationMinutes;

        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(this.durationMinutes, TimeUnit.MINUTES)
                .maximumSize(1000)
                .build();
    }

    public void saveCache(K key, V value) {
        cache.put(key, value);
    }

    public V getCache(K key) {
        return cache.getIfPresent(key);
    }

    public void invalidateCache(K key) {
        cache.invalidate(key);
    }
}
