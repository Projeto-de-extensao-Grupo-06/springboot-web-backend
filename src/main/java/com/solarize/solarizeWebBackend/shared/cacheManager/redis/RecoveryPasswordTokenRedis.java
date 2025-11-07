package com.solarize.solarizeWebBackend.shared.cacheManager.redis;

import com.solarize.solarizeWebBackend.shared.cacheManager.cacheInterfaces.RecoveryPasswordTokenCache;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@RequiredArgsConstructor
public class RecoveryPasswordTokenRedis implements RecoveryPasswordTokenCache {
    final private RedisTemplate<String, String> redisTemplate;

    @Override
    public void saveCache(String key, String value) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(15));
    }

    @Override
    public String getCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void invalidateCache(String key) {
        redisTemplate.delete(key);
    }
}
