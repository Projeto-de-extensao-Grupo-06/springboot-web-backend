package com.solarize.solarizeWebBackend.modules.permissionGroup;

import com.solarize.solarizeWebBackend.modules.permissionGroup.annotation.ModulePermission;
import com.solarize.solarizeWebBackend.modules.permissionGroup.dtos.PermissionGroupDto;
import com.solarize.solarizeWebBackend.modules.permissionGroup.dtos.ModulePermissionsDto;
import com.solarize.solarizeWebBackend.shared.exceptions.BadRequestException;
import com.solarize.solarizeWebBackend.shared.exceptions.MappingException;
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
                    Object bitMaskO = f.get(permissionGroup);
                    int bitMask;

                    if(bitMaskO == null) {
                        continue;
                    }

                    bitMask = (int) bitMaskO;

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
                .mainModule(permissionGroup.getMainModule())
                .permissions(permissions)
                .build();
    }

    public static PermissionGroup toEntity(PermissionGroupDto dto) throws IllegalAccessException {
        List<ModulePermissionsDto> modulePermissions = dto.getPermissions();

        Map<String, Integer> permissionsMap = modulePermissions.stream().collect(Collectors.toMap(
                ModulePermissionsDto::getModuleName,
                m -> {
                    String binary = String.format("%d%d%d%d",
                            m.getDelete() ? 1 : 0,
                            m.getUpdate() ? 1 : 0,
                            m.getWrite() ? 1 : 0,
                            m.getRead() ? 1 : 0
                    );

                    return Integer.parseInt(binary, 2);
                }
        ));

        PermissionGroup permissionGroup = new PermissionGroup();
        permissionGroup.setRole(dto.getRole());
        boolean validMainScreen = false;

        for(Field f : permissionGroup.getClass().getDeclaredFields()) {
            if(f.isAnnotationPresent(ModulePermission.class)) {
                f.setAccessible(true);

                String module = f.getAnnotation(ModulePermission.class).value();

                Integer permission = permissionsMap.get(module);

                if(permission == null) {
                    throw new MappingException("Module " + module + " does not have a permission defined.");
                }
 
                f.set(permissionGroup, permission);


                if(!validMainScreen) validMainScreen = module.equals(dto.getMainModule());

            }
        }

        if(!validMainScreen) {
            throw new MappingException("Main module " + dto.getMainModule() + " does not exist.");
        }

        permissionGroup.setMainModule(dto.getMainModule());

        return permissionGroup;
    }
}
