package com.solarize.solarizeWebBackend.modules.permissionGroup;

import com.solarize.solarizeWebBackend.modules.permissionGroup.dtos.GetRolesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission-groups")
@RequiredArgsConstructor
public class PermissionGroupController {
    private final PermissionGroupService service;

    @GetMapping("/{id}")
    public ResponseEntity<GetRolesDto> getRolesById(@PathVariable int id) {
        PermissionGroup pg = this.service.permissionGroupById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PermissionGroupMapper.toDto(pg));
    }
}
