package com.solarize.solarizeWebBackend.modules.address.projection;

import com.solarize.solarizeWebBackend.modules.address.enumerated.BrazilianState;

public interface StateCityProjection {
    BrazilianState getState();
    String getCity();
}
