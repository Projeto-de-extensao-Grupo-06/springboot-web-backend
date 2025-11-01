package com.solarize.solarizeWebBackend.shared.cacheManager;

import com.solarize.solarizeWebBackend.shared.cacheManager.cacheInterfaces.OTPCache;
import com.solarize.solarizeWebBackend.shared.cacheManager.caffeine.OTPCaffeine;
import com.solarize.solarizeWebBackend.shared.cacheManager.redis.OTPRedis;
import com.solarize.solarizeWebBackend.shared.cacheManager.redis.Templates;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class OTPCacheConfig {
    @Bean
    @Profile("dev")
    public OTPCache otpCaffeineCache () {
        return new OTPCaffeine();
    }

    @Bean
    @Profile("prod")
    public OTPCache otpRedisCache(@Qualifier("otpTemplate") RedisTemplate<String, String> template) {
        return new OTPRedis(template);
    }
}
