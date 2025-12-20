package com.solarize.solarizeWebBackend.modules.budget.dto.request;

import com.solarize.solarizeWebBackend.modules.budget.enumerated.ParameterValueType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UpdatePersonalizedParametersDto {
    @NotNull
    @Valid
    private List<PersonalizedParameterDto> personalizedParameters;

    @Data
    public static class PersonalizedParameterDto {
        private Long id;

        @NotBlank
        private String name;

        @NotNull
        private ParameterValueType type;

        @PositiveOrZero
        private Double value;
    }
}
