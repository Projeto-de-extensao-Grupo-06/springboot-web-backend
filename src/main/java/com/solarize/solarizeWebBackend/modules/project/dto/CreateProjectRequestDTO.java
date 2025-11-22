package com.solarize.solarizeWebBackend.modules.project.dto;

import com.solarize.solarizeWebBackend.modules.project.SystemTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectRequestDTO {

    @NotEmpty(message = "The project name is required")
    @Size(max = 60, message = "The project name can´t exceed 60 characters")
    private String name;

    @NotEmpty(message = "Client ID is required")
    private Long clientId;

    private String description;
    private Long addressId;
    private SystemTypeEnum systemTypeEnum;

}
