package com.solarize.solarizeWebBackend.modules.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectBotCreateDto {

    @NotNull(message = "Client ID is required")
    private Long clientId;

    @NotBlank(message = "System type is required")
    private String systemType;

    @NotNull(message = "Estimated power (kWp) is required")
    private Double estimatedPower;

    @NotNull(message = "Estimated cost is required")
    private Double estimatedCost;

    @NotNull(message = "Estimated savings is required")
    private Double estimatedSavings;
}
