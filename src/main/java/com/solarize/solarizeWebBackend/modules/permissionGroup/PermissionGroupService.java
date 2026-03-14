package com.solarize.solarizeWebBackend.modules.permissionGroup;

import com.solarize.solarizeWebBackend.modules.permissionGroup.annotation.ModulePermission;
import com.solarize.solarizeWebBackend.modules.permissionGroup.dtos.PermissionGroupDto;
import com.solarize.solarizeWebBackend.shared.exceptions.BadRequestException;
import com.solarize.solarizeWebBackend.shared.exceptions.ConflictException;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
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
        boolean isPermissionGroupExists = repository.existsByRole(permissionGroup.getRole());

        if(isPermissionGroupExists) {
            throw new ConflictException("A permission group with this name already exists.");
        }

        return repository.save(permissionGroup);
    }

    public PermissionGroup updatePermissionGroup(Long id, PermissionGroup updatedData) {
        PermissionGroup existing = repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("PermissionGroup with id " + id + " does not exist.")
                );

        if (!existing.getRole().equals(updatedData.getRole())) {
            boolean exists = repository.existsByRole(updatedData.getRole());

            if (exists) {
                throw new ConflictException("A permission group with this name already exists.");
            }
        }

        existing.setRole(updatedData.getRole());
        existing.setMainModule(updatedData.getMainModule());

        Field[] fields = PermissionGroup.class.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(ModulePermission.class)) {
                try {
                    field.setAccessible(true);
                    field.set(existing, field.get(updatedData));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error updating module permissions");
                }
            }
        }

        return repository.save(existing);
    }

    public void deletePermissionGroup(Long id) {
        PermissionGroup existing = repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("PermissionGroup with id " + id + " does not exist.")
                );


        Boolean isCoworkerInGroup = repository.hasCoworkerInGroup(existing);

        if(isCoworkerInGroup) {
            throw new ConflictException("This permissionGroup has a coworker and cannot be deleted.");
        }

        if(existing.getRole().equalsIgnoreCase("ADMIN")) {
            throw new BadRequestException("Cannot delete ADMIN role.");
        }

        repository.delete(existing);
    }

}
