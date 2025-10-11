package com.solarize.solarizeWebBackend.modules.permissionGroup.permissionStrategy;

import com.solarize.solarizeWebBackend.modules.permissionGroup.PermissionMask;

public class UpdateVerifier implements PermissionVerifier{
    @Override
    public boolean verifyPermission(int permissionMask) {
        return (permissionMask & PermissionMask.UPDATE.mask) == PermissionMask.UPDATE.mask;
    }
}
