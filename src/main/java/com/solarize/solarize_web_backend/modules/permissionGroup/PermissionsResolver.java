package com.solarize.solarize_web_backend.modules.permissionGroup;

import com.solarize.solarize_web_backend.modules.permissionGroup.annotation.ModulePermission;
import com.solarize.solarize_web_backend.modules.permissionGroup.annotation.Role;
import com.solarize.solarize_web_backend.modules.permissionGroup.permissionStrategy.PermissionVerifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PermissionsResolver {
    private final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

    public List<GrantedAuthority> resolve(Object permissionSource) {
        this.resolveRole(permissionSource);
        this.resolveModulePermissions(permissionSource);

        return this.grantedAuthorities;
    }

    private void resolveRole(Object permissionSource) {
        Field[] fields = permissionSource.getClass().getDeclaredFields();

        for (Field field : fields) {
            if(field.isAnnotationPresent(Role.class)) {
                try {
                    field.setAccessible(true);
                    Role role = field.getAnnotation(Role.class);
                    String roleName = (String) field.get(permissionSource);

                    this.grantedAuthorities.add(new SimpleGrantedAuthority(role.value() + "_" + roleName.toUpperCase()));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void resolveModulePermissions(Object permissionSource) {
        Field[] fields = permissionSource.getClass().getDeclaredFields();

        for (Field field : fields) {
            if(field.isAnnotationPresent(ModulePermission.class)) {
                try {
                    field.setAccessible(true);
                    int bitMask = (int) field.get(permissionSource);
                    String moduleName =  field.getAnnotation(ModulePermission.class).value();

                    for(PermissionMask permissionMask : PermissionMask.values()) {
                        Constructor<? extends PermissionVerifier> constructor = permissionMask.permissionVerifierClass.getConstructor();
                        PermissionVerifier permissionVerifier = constructor.newInstance();

                        if(permissionVerifier.verifyPermission(bitMask)) {
                            this.grantedAuthorities.add(new SimpleGrantedAuthority(moduleName + "_" + permissionMask.name()));
                        }
                    }
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException |
                         InstantiationException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
