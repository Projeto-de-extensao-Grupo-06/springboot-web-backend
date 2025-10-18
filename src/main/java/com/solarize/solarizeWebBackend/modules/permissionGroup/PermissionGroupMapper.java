package com.solarize.solarizeWebBackend.modules.permissionGroup;

import com.solarize.solarizeWebBackend.modules.permissionGroup.annotation.ModulePermission;
import com.solarize.solarizeWebBackend.modules.permissionGroup.dtos.PermissionGroupDto;
import com.solarize.solarizeWebBackend.modules.permissionGroup.dtos.ModulePermissionsDto;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class PermissionGroupMapper {
    public static PermissionGroupDto toDto(PermissionGroup permissionGroup) {
        List<ModulePermissionsDto> permissions = new ArrayList<>();

        Field[] fields = permissionGroup.getClass().getDeclaredFields();

        for(Field f : fields) {
            if(f.isAnnotationPresent(ModulePermission.class)) {
                try {
                    f.setAccessible(true);
                    String moduleName = f.getAnnotation(ModulePermission.class).value();
                    int bitMask = (int) f.get(permissionGroup);

                    permissions.add(
                            ModulePermissionsDto
                                    .builder()
                                    .moduleName(moduleName)
                                    .read(PermissionMask.READ.permissionVerifierClass.verifyPermission(bitMask))
                                    .write(PermissionMask.WRITE.permissionVerifierClass.verifyPermission(bitMask))
                                    .update(PermissionMask.UPDATE.permissionVerifierClass.verifyPermission(bitMask))
                                    .delete(PermissionMask.DELETE.permissionVerifierClass.verifyPermission(bitMask))
                                    .build()
                    );

                } catch(IllegalAccessException e){
                    log.error(e.getMessage());
                }

            }
        }

        return PermissionGroupDto
                .builder()
                .role(permissionGroup.getRole())
                .permissions(permissions)
                .build();
    }

    public static PermissionGroup toEntity(PermissionGroupDto dto) throws IllegalAccessException {
        List<ModulePermissionsDto> modulePermissions = dto.getPermissions();

        Map<String, Integer> permissionsMap = modulePermissions.stream().collect(Collectors.toMap(
                ModulePermissionsDto::getModuleName,
                m -> {
                    String binary = String.format("%d%d%d%d",
                            m.isDelete() ? 1 : 0,
                            m.isUpdate() ? 1 : 0,
                            m.isWrite() ? 1 : 0,
                            m.isWrite() ? 1 : 0
                    );

                    return Integer.parseInt(binary, 2);
                }
        ));

        PermissionGroup permissionGroup = new PermissionGroup();
        permissionGroup.setRole(dto.getRole());

        for(Field f : permissionGroup.getClass().getDeclaredFields()) {
            if(f.isAnnotationPresent(ModulePermission.class)) {
                f.setAccessible(true);

                Integer permission = permissionsMap.get(f.getAnnotation(ModulePermission.class).value());

                if(permission != null) {
                    f.set(permissionGroup, permission);
                }
            }
        }

        return permissionGroup;
    }

}
