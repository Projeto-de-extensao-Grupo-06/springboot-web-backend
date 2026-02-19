package com.solarize.solarizeWebBackend.modules.permissionGroup;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PermissionGroupRepository extends JpaRepository<PermissionGroup, Long> {
    Boolean existsByRole(String role);
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Coworker c WHERE c.permission = :role")
    Boolean existsInCoworker(@Param("role") PermissionGroup permissionGroup);
}
