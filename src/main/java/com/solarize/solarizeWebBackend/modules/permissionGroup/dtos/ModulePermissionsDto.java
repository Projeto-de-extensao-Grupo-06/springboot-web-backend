package com.solarize.solarizeWebBackend.modules.permissionGroup.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ModulePermissionsDto {
    @NotBlank
    private String moduleName;

    @NotNull
    private Boolean read;

    @NotNull
    private Boolean write;

    @NotNull
    private Boolean update;

    @NotNull
    private Boolean delete;
}
