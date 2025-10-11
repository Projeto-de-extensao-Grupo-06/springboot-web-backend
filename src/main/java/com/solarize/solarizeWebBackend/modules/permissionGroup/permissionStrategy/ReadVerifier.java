package com.solarize.solarizeWebBackend.modules.permissionGroup.permissionStrategy;

import com.solarize.solarizeWebBackend.modules.permissionGroup.PermissionMask;

public class ReadVerifier implements PermissionVerifier {
    @Override
    public boolean verifyPermission(int permissionMask) {
        return (permissionMask & PermissionMask.READ.mask) == PermissionMask.READ.mask;
    }
}
