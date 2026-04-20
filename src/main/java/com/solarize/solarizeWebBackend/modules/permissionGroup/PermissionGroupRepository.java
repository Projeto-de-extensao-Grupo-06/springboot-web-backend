package com.solarize.solarizeWebBackend.modules.permissionGroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PermissionGroupRepository extends JpaRepository<PermissionGroup, Long> {
    Boolean existsByRole(String role);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Coworker c WHERE c.permission = :permissionGroup")
    Boolean hasCoworkerInGroup(@Param("permissionGroup") PermissionGroup permissionGroup);


    @Query("SELECT COUNT(c) FROM Coworker c WHERE c.permission = :permissionGroup")
    Long countCoworkersInGroup(@Param("permissionGroup") PermissionGroup permissionGroup);


    @Query("""
        SELECT pg FROM PermissionGroup pg
        WHERE (
            :search IS NULL OR
            CAST(FUNCTION('UNACCENT', LOWER(pg.role)) AS string)
                LIKE CONCAT('%', CAST(FUNCTION('UNACCENT', LOWER(:search)) AS string), '%')
        )
        AND (
            :mainModule IS NULL OR pg.mainModule = :mainModule
        )
    """)
    Page<PermissionGroup> findAllWithFilters(
            @Param("search") String search,
            @Param("mainModule") String mainModule,
            Pageable pageable
    );
}
