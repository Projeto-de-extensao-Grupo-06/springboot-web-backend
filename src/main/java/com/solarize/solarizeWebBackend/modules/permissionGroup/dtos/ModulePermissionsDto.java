package com.solarize.solarizeWebBackend.modules.permissionGroup.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ModulePermissionsDto {
    private String moduleName;
    private boolean read;
    private boolean write;
    private boolean update;
    private boolean delete;
}
