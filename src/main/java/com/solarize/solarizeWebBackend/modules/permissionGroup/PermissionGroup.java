package com.solarize.solarizeWebBackend.modules.permissionGroup;

import com.solarize.solarizeWebBackend.modules.permissionGroup.annotation.ModulePermission;
import com.solarize.solarizeWebBackend.modules.permissionGroup.annotation.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class PermissionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permission_group")
    private Long id;

    @Role
    private String role;

    private String mainModule;

    @ModulePermission("CLIENT")
    private Integer accessClient;

    @ModulePermission("PROJECT")
    private Integer accessProject;

    @ModulePermission("BUDGET")
    private Integer accessBudget;

    @ModulePermission("SCHEDULE")
    private Integer accessSchedule;
}
