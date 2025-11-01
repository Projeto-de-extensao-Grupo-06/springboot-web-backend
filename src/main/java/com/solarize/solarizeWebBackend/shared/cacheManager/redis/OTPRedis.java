package com.solarize.solarizeWebBackend.shared.cacheManager.redis;

import com.solarize.solarizeWebBackend.shared.cacheManager.cacheInterfaces.OTPCache;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@RequiredArgsConstructor
public class OTPRedis implements OTPCache {
    final private RedisTemplate<String, String> redisTemplate;

    @Override
    public void saveCache(String code, String userId) {
        redisTemplate.opsForValue().set(code, userId, Duration.ofMinutes(10));
    }

    @Override
    public String getCache(String code) {
        return redisTemplate.opsForValue().get(code);
    }

    @Override
    public void invalidateCache(String code) {
        redisTemplate.delete(code);
    }
}
