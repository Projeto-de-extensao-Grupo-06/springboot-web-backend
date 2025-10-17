package com.solarize.solarizeWebBackend.modules.permissionGroup.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetRolesDto {
    private String role;
    private List<ModulePermissionsDto> permissions;
}
