package com.solarize.solarizeWebBackend.shared.cacheManager;

import com.solarize.solarizeWebBackend.shared.cacheManager.cacheInterfaces.RecoveryPasswordTokenCache;
import com.solarize.solarizeWebBackend.shared.cacheManager.caffeine.RecoveryPasswordTokenCaffeine;
import com.solarize.solarizeWebBackend.shared.cacheManager.redis.RecoveryPasswordTokenRedis;
import com.solarize.solarizeWebBackend.shared.cacheManager.redis.Templates;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RecoveryPasswordTokenCacheConfig {
    @Bean
    @Profile("dev")
    public RecoveryPasswordTokenCache recoveryPasswordTokenCaffeineCache() {
        return new RecoveryPasswordTokenCaffeine();
    }

    @Bean
    @Profile("prod")
    public RecoveryPasswordTokenCache recoveryPasswordTokenRedisCache(@Qualifier("recoveryTokenTemplate") RedisTemplate<String, String> template) {
        return new RecoveryPasswordTokenRedis(template);
    }
}
