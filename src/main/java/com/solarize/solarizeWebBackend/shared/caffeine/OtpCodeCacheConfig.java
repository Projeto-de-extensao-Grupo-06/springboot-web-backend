package com.solarize.solarizeWebBackend.shared.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class OtpCodeCacheConfig {
    private final Cache<String, String> cache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(1000)
            .build();


    public void saveOtp(String code, String userId) {
        cache.put(code, userId);
    }

    public String getUserId(String code) {
        return cache.getIfPresent(code);
    }

    public void invalidateCode(String code) {
        cache.invalidate(code);
    }

}
