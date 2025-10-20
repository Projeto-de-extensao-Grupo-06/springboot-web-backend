package com.solarize.solarizeWebBackend.modules.permissionGroup;

import com.solarize.solarizeWebBackend.modules.permissionGroup.dtos.GetPermissionGroupDto;
import com.solarize.solarizeWebBackend.modules.permissionGroup.dtos.PermissionGroupDto;
import com.solarize.solarizeWebBackend.shared.exceptions.BadRequestException;
import com.solarize.solarizeWebBackend.shared.exceptions.MappingException;
import com.solarize.solarizeWebBackend.shared.exceptions.ServerErrorException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permission-groups")
@RequiredArgsConstructor
public class PermissionGroupController {
    private final PermissionGroupService service;

    @GetMapping("/{id}")
    public ResponseEntity<GetPermissionGroupDto> getRolesById(@PathVariable int id) {
        PermissionGroup pg = this.service.permissionGroupById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PermissionGroupMapper.toDto(pg));
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<GetPermissionGroupDto> createRole(@RequestBody @Valid PermissionGroupDto dto) {
        try {
            PermissionGroup createdPermissionGroup = service.createPermissionGroup(PermissionGroupMapper.toEntity(dto));

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(PermissionGroupMapper.toDto(createdPermissionGroup));

        } catch (IllegalAccessException e) {
            throw new ServerErrorException("Error mapping DTO to entity during permission group creation");
        } catch (MappingException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
