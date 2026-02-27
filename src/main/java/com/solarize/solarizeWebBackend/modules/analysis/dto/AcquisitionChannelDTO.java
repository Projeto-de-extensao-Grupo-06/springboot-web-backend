package com.solarize.solarizeWebBackend.modules.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcquisitionChannelDTO {
    private String name;
    private Long projectCount;
    private Double percentage;
}
