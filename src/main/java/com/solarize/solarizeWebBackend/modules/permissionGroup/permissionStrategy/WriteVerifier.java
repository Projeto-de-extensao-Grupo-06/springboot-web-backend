package com.solarize.solarizeWebBackend.modules.permissionGroup.permissionStrategy;

import com.solarize.solarizeWebBackend.modules.permissionGroup.PermissionMask;

public class WriteVerifier implements PermissionVerifier {
    @Override
    public boolean verifyPermission(int permissionMask) {
        return (permissionMask & PermissionMask.WRITE.mask) == PermissionMask.WRITE.mask;
    }
}
