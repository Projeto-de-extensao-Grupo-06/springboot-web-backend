package com.solarize.solarizeWebBackend.shared.caffeine;

import org.springframework.stereotype.Component;

@Component
public class OTPCacheManager extends CacheManager<String, String>{
    public OTPCacheManager() {
        super(10);
    }
}
