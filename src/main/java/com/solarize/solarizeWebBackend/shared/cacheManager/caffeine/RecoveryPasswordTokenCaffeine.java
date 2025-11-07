package com.solarize.solarizeWebBackend.shared.cacheManager.caffeine;

import com.solarize.solarizeWebBackend.shared.cacheManager.cacheInterfaces.RecoveryPasswordTokenCache;

public class RecoveryPasswordTokenCaffeine extends CaffeineManager<String, String> implements RecoveryPasswordTokenCache {
    public RecoveryPasswordTokenCaffeine() {
        super(15);
    }
}
