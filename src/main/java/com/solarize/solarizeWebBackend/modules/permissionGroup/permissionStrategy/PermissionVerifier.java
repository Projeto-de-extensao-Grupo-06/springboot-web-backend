package com.solarize.solarizeWebBackend.modules.permissionGroup.permissionStrategy;

public interface PermissionVerifier {
    public boolean verifyPermission(int permissionMask);
}


