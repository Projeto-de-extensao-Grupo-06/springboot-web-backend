package com.solarize.solarizeWebBackend.modules.budget.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Data
public class UpdateFixedParametersDto {
    @Valid
    private List<FixedParameterDto> fixedParamaters;

    @Data
    public static class FixedParameterDto {
        @NotNull
        private String parameterName;

        @PositiveOrZero
        @NotNull
        private Double value;
    }
}
