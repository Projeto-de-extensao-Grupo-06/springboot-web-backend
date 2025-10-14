package com.solarize.solarizeWebBackend.modules.permissionGroup;

import com.solarize.solarizeWebBackend.modules.permissionGroup.annotation.ModulePermission;
import com.solarize.solarizeWebBackend.modules.permissionGroup.dtos.GetRolesDto;
import com.solarize.solarizeWebBackend.modules.permissionGroup.dtos.ModulePermissionsDto;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PermissionGroupMapper {
    public static GetRolesDto toDto(PermissionGroup permissionGroup) {
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

        return GetRolesDto
                .builder()
                .role(permissionGroup.getRole())
                .permissions(permissions)
                .build();
    }



}
