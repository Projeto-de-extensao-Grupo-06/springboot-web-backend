package com.solarize.solarizeWebBackend.modules.permissionGroup;

import com.solarize.solarizeWebBackend.modules.permissionGroup.permissionStrategy.PermissionVerifier;
import com.solarize.solarizeWebBackend.modules.permissionGroup.permissionStrategy.ReadVerifier;
import com.solarize.solarizeWebBackend.modules.permissionGroup.permissionStrategy.WriteVerifier;
import com.solarize.solarizeWebBackend.modules.permissionGroup.permissionStrategy.UpdateVerifier;
import com.solarize.solarizeWebBackend.modules.permissionGroup.permissionStrategy.DeleteVerifier;

public enum PermissionMask {
    READ(0x1, "READ", new ReadVerifier()),
    WRITE(0x2, "WRITE", new WriteVerifier()),
    UPDATE(0X4, "UPDATE", new UpdateVerifier()),
    DELETE(0X8, "DELETE", new DeleteVerifier());

    public final int mask;
    public final String name;
    public final PermissionVerifier permissionVerifierClass;

    PermissionMask(
            int mask,
            String name,
            PermissionVerifier permissionVerifierClass
    ) {
        this.mask = mask;
        this.name = name;
        this.permissionVerifierClass = permissionVerifierClass;
    }
}
