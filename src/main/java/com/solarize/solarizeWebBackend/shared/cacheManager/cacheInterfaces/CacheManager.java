package com.solarize.solarizeWebBackend.shared.cacheManager.cacheInterfaces;

public interface CacheManager<K, V> {
    void saveCache(K code, V userId);
    V getCache(K code);
    void invalidateCache(K code);
}
