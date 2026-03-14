package com.solarize.solarizeWebBackend.modules.coworker.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoworkerResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    @Schema(description = "ID do grupo de permissão", example = "1")
    private Long permissionGroupId;

    @Schema(description = "Role do grupo de permissão", example = "ADMIN")
    private String permissionGroupRole;
}
