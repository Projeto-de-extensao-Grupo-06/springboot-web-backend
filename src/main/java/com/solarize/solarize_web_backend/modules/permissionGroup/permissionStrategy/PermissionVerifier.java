package com.solarize.solarize_web_backend.modules.permissionGroup.permissionStrategy;

public interface PermissionVerifier {
    public boolean verifyPermission(int permissionMask);
}
