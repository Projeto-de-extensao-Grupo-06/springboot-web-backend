package com.solarize.solarizeWebBackend.modules.coworker;

import com.solarize.solarizeWebBackend.modules.permissionGroup.PermissionGroup;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(
        name = "coworker",
        indexes = {
                @Index(name = "idx_coworker_email", columnList = "email", unique = true)
        }
)
public class Coworker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coworker")
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_permission_group")
    private PermissionGroup permission;
}
