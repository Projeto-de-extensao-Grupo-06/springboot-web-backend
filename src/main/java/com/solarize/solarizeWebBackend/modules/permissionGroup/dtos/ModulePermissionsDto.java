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
    private boolean read;

    @NotNull
    private boolean write;

    @NotNull
    private boolean update;

    @NotNull
    private boolean delete;
}
