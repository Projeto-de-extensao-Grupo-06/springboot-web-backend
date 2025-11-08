package com.solarize.solarizeWebBackend.shared.cacheManager.caffeine;

import com.solarize.solarizeWebBackend.shared.cacheManager.cacheInterfaces.OTPCache;

public class OTPCaffeine extends CaffeineManager<String, String> implements OTPCache {
    public OTPCaffeine() {
        super(10);
    }
}
