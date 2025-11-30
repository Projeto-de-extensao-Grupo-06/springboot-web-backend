package com.solarize.solarizeWebBackend.modules.budget.dto.request;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.FixedParameterName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Data
public class UpdateFixedParametersDto {
    @Valid
    private List<FixedParameterDto> fixedParameters;

    @Data
    public static class FixedParameterDto {
        @NotNull
        private FixedParameterName parameterName;

        @PositiveOrZero
        @NotNull
        private Double value;
    }
}
