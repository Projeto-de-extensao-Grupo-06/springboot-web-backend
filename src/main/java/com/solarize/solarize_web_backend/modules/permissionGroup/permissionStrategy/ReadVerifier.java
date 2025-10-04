package com.solarize.solarize_web_backend.modules.permissionGroup.permissionStrategy;

import com.solarize.solarize_web_backend.modules.permissionGroup.PermissionMask;

public class ReadVerifier implements PermissionVerifier {
    @Override
    public boolean verifyPermission(int permissionMask) {
        return (permissionMask & PermissionMask.READ.mask) == PermissionMask.READ.mask;
    }
}
