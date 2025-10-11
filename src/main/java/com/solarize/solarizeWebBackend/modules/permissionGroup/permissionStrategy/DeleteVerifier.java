package com.solarize.solarizeWebBackend.modules.permissionGroup.permissionStrategy;

import com.solarize.solarizeWebBackend.modules.permissionGroup.PermissionMask;

public class DeleteVerifier implements PermissionVerifier {
    @Override
    public boolean verifyPermission(int permissionMask) {
        return (permissionMask & PermissionMask.DELETE.mask) == PermissionMask.DELETE.mask;
    }
}
