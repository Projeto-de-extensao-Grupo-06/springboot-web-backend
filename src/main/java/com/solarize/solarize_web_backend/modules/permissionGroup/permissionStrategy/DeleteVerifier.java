package com.solarize.solarize_web_backend.modules.permissionGroup.permissionStrategy;

import com.solarize.solarize_web_backend.modules.permissionGroup.PermissionMask;

public class DeleteVerifier implements PermissionVerifier {
    @Override
    public boolean verifyPermission(int permissionMask) {
        return (permissionMask & PermissionMask.DELETE.mask) == PermissionMask.DELETE.mask;
    }
}
