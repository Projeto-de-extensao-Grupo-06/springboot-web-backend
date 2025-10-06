package com.solarize.solarize_web_backend.modules.permissionGroup;

import com.solarize.solarize_web_backend.modules.permissionGroup.permissionStrategy.PermissionVerifier;
import com.solarize.solarize_web_backend.modules.permissionGroup.permissionStrategy.ReadVerifier;
import com.solarize.solarize_web_backend.modules.permissionGroup.permissionStrategy.WriteVerifier;
import com.solarize.solarize_web_backend.modules.permissionGroup.permissionStrategy.UpdateVerifier;
import com.solarize.solarize_web_backend.modules.permissionGroup.permissionStrategy.DeleteVerifier;

public enum PermissionMask {
    READ(0x1, "READ", ReadVerifier.class),
    WRITE(0x2, "WRITE", WriteVerifier.class),
    UPDATE(0X4, "UPDATE", UpdateVerifier.class),
    DELETE(0X8, "DELETE", DeleteVerifier.class);

    public final int mask;
    public final String name;
    public final Class<? extends PermissionVerifier> permissionVerifierClass;

    PermissionMask(
            int mask,
            String name,
            Class<? extends PermissionVerifier> permissionVerifierClass
    ) {
        this.mask = mask;
        this.name = name;
        this.permissionVerifierClass = permissionVerifierClass;
    }
}
