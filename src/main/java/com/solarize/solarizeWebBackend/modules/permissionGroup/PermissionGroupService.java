package com.solarize.solarizeWebBackend.modules.permissionGroup;

import com.solarize.solarizeWebBackend.modules.permissionGroup.dtos.PermissionGroupDto;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionGroupService {
    final PermissionGroupRepository repository;

    public PermissionGroup permissionGroupById (long id) {
        Optional<PermissionGroup> permissionGroup = repository.findById(id);

        if(permissionGroup.isEmpty()) {
            throw new NotFoundException("PermissionGroup with id " + id + " does not exist.");
        }

        return permissionGroup.get();
    }

    public PermissionGroup createPermissionGroup(PermissionGroup permissionGroup) {
        return repository.save(permissionGroup);
    }
}
