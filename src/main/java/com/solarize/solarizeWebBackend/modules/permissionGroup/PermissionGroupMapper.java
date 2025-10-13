package com.solarize.solarizeWebBackend.modules.permissionGroup;

import com.solarize.solarizeWebBackend.modules.permissionGroup.annotation.ModulePermission;
import com.solarize.solarizeWebBackend.modules.permissionGroup.dtos.ModulePermissionsDto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PermissionGroupMapper {
    private static List<ModulePermissionsDto> bitMaskToModulePermissionDto(PermissionGroup permissionGroup) {
        List<ModulePermissionsDto> permissions = new ArrayList<>();

        Field[] fields = permissionGroup.getClass().getFields();

        for(Field f : fields) {
            if(f.isAnnotationPresent(ModulePermission.class)) {
                try {
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
                    e.printStackTrace();
                }

            }
        }

        return permissions;
    }
}
