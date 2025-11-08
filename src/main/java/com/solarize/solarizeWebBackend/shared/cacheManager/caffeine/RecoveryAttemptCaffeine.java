package com.solarize.solarizeWebBackend.shared.cacheManager.caffeine;

import com.solarize.solarizeWebBackend.shared.cacheManager.cacheInterfaces.RecoveryAttemptCache;

public class RecoveryAttemptCaffeine extends CaffeineManager<String, Boolean> implements RecoveryAttemptCache {
    public RecoveryAttemptCaffeine() {
        super(1);
    }
}
