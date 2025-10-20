package com.solarize.solarizeWebBackend.modules.permissionGroup.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetPermissionGroupDto {
    private Long id;
    private String role;
    private String mainModule ;
    private List<ModulePermissionsDto> permissions;
}
