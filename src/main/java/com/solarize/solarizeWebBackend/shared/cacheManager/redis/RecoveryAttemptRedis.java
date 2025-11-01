package com.solarize.solarizeWebBackend.shared.cacheManager.redis;

import com.solarize.solarizeWebBackend.shared.cacheManager.cacheInterfaces.RecoveryAttemptCache;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@RequiredArgsConstructor
public class RecoveryAttemptRedis implements RecoveryAttemptCache {
    final private RedisTemplate<String, Boolean> redisTemplate;

    @Override
    public void saveCache(String key, Boolean value) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(1));
    }

    @Override
    public Boolean getCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void invalidateCache(String key) {
        redisTemplate.delete(key);
    }
}
