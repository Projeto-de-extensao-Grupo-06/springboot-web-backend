package com.solarize.solarizeWebBackend.shared.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

public abstract class CacheManager<K, V> {
    final int durationMinutes;
    private final Cache<K, V> cache;

    public CacheManager(int durationMinutes) {
        this.durationMinutes = durationMinutes;

        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(this.durationMinutes, TimeUnit.MINUTES)
                .maximumSize(1000)
                .build();
    }




    public void saveCache(K code, V userId) {
        cache.put(code, userId);
    }

    public V getCache(K code) {
        return cache.getIfPresent(code);
    }

    public void invalidateCache(K code) {
        cache.invalidate(code);
    }
}
