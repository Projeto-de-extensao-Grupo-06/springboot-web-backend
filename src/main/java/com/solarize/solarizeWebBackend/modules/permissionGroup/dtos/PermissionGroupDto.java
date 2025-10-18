package com.solarize.solarizeWebBackend.modules.permissionGroup.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PermissionGroupDto {
    @NotBlank
    private String role;

    @NotBlank
    private String mainModule;

    @NotEmpty
    @Valid
    private List<ModulePermissionsDto> permissions;
}
