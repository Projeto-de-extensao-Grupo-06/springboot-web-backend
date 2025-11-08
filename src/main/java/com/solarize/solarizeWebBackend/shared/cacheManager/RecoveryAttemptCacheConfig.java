package com.solarize.solarizeWebBackend.shared.cacheManager;

import com.solarize.solarizeWebBackend.shared.cacheManager.cacheInterfaces.RecoveryAttemptCache;
import com.solarize.solarizeWebBackend.shared.cacheManager.caffeine.RecoveryAttemptCaffeine;
import com.solarize.solarizeWebBackend.shared.cacheManager.redis.RecoveryAttemptRedis;
import com.solarize.solarizeWebBackend.shared.cacheManager.redis.Templates;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RecoveryAttemptCacheConfig {
    @Bean
    @Profile("dev")
    public RecoveryAttemptCache recoveryAttemptCaffeineCache() {
        return new RecoveryAttemptCaffeine();
    }

    @Bean
    @Profile("prod")
    public RecoveryAttemptCache recoveryAttemptRedisCache(@Qualifier("recoveryAttemptTemplate") RedisTemplate<String, Boolean> template) {
        return new RecoveryAttemptRedis(template);
    }
}
