package com.solarize.solarize_web_backend.modules.permissionGroup.permissionStrategy;

import com.solarize.solarize_web_backend.modules.permissionGroup.PermissionMask;

public class WriteVerifier implements PermissionVerifier {
    @Override
    public boolean verifyPermission(int permissionMask) {
        return (permissionMask & PermissionMask.WRITE.mask) == PermissionMask.WRITE.mask;
    }
}
