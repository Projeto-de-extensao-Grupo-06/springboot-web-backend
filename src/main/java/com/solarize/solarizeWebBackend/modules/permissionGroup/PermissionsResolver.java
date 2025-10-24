package com.solarize.solarizeWebBackend.modules.permissionGroup;

import com.solarize.solarizeWebBackend.modules.permissionGroup.annotation.ModulePermission;
import com.solarize.solarizeWebBackend.modules.permissionGroup.annotation.Role;
import com.solarize.solarizeWebBackend.modules.permissionGroup.dtos.ModulePermissionsDto;
import com.solarize.solarizeWebBackend.modules.permissionGroup.permissionStrategy.PermissionVerifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class PermissionsResolver {
    static public List<GrantedAuthority> resolve(PermissionGroup permissionSource) {
        final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        resolveRole(permissionSource, grantedAuthorities);
        resolveModulePermissions(permissionSource, grantedAuthorities);

        return grantedAuthorities;
    }

    private static void resolveRole(PermissionGroup permissionSource, List<GrantedAuthority> grantedAuthorities) {
        Field[] fields = permissionSource.getClass().getDeclaredFields();

        for (Field field : fields) {
            if(field.isAnnotationPresent(Role.class)) {
                try {
                    field.setAccessible(true);
                    Role role = field.getAnnotation(Role.class);
                    String roleName = (String) field.get(permissionSource);

                    grantedAuthorities.add(new SimpleGrantedAuthority(role.value() + "_" + roleName.toUpperCase()));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void resolveModulePermissions(PermissionGroup permissionSource, List<GrantedAuthority> grantedAuthorities) {
        Field[] fields = permissionSource.getClass().getDeclaredFields();

        for (Field field : fields) {
            if(field.isAnnotationPresent(ModulePermission.class)) {
                try {
                    field.setAccessible(true);
                    int bitMask = (int) field.get(permissionSource);
                    String moduleName =  field.getAnnotation(ModulePermission.class).value();

                    for(PermissionMask permissionMask : PermissionMask.values()) {
                        PermissionVerifier permissionVerifier = permissionMask.permissionVerifierClass;

                        if(permissionVerifier.verifyPermission(bitMask)) {
                            grantedAuthorities.add(new SimpleGrantedAuthority(moduleName + "_" + permissionMask.name()));
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public static List<String> listSystemModules(Class<PermissionGroup> permissionGroupClass) {
        Field[] fields = permissionGroupClass.getDeclaredFields();
        List<String> moduleNames = new ArrayList<>();

        for(Field f : fields) {
            if(f.isAnnotationPresent(ModulePermission.class)) {
                f.setAccessible(true);
                String moduleName = f.getAnnotation(ModulePermission.class).value();
                moduleNames.add(moduleName);
            }
        }

        return moduleNames;
    }
}
