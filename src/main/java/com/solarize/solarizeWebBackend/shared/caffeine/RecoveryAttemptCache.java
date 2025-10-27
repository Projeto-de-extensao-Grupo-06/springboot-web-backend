package com.solarize.solarizeWebBackend.shared.caffeine;

import org.springframework.stereotype.Component;

@Component
public class RecoveryAttemptCache extends CacheManager<String, Boolean>{
    public RecoveryAttemptCache() {
        super(1);
    }
}
