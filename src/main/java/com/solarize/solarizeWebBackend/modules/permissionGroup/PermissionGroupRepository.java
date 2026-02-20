package com.solarize.solarizeWebBackend.modules.permissionGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PermissionGroupRepository extends JpaRepository<PermissionGroup, Long> {
    Boolean existsByRole(String role);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Coworker c WHERE c.permission = :permissionGroup")
    Boolean hasCoworkerInGroup(@Param("permissionGroup") PermissionGroup permissionGroup);
}
