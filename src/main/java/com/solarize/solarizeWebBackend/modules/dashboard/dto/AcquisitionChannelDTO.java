
package com.solarize.solarizeWebBackend.modules.dashboard.dto;

public record AcquisitionChannelDTO(
    String name,
    Long projectCount,
    Double percentage
) {}
