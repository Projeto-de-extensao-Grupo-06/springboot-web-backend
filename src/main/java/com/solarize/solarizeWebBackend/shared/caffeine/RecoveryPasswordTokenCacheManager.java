package com.solarize.solarizeWebBackend.shared.caffeine;

import org.springframework.stereotype.Component;

@Component
public class RecoveryPasswordTokenCacheManager extends CacheManager<String, Integer>{}
