package com.solarize.solarizeWebBackend.modules.coworker;

import com.solarize.solarizeWebBackend.modules.permissionGroup.PermissionGroupService;
import com.solarize.solarizeWebBackend.shared.exceptions.ConflictException;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoworkerService {
    private final PasswordEncoder passwordEncoder;
    private final CoworkerRepository REPOSITORY;
    private final PermissionGroupService permissionGroupService;

    public Coworker createCoworker(Coworker coworker) {
        validateConflict(coworker);
        validateAdminCreation(coworker);
        validatePermissionGroup(coworker);
        coworker.setPermission(permissionGroupService.permissionGroupById(coworker.getPermission().getId()));
        coworker.setPassword(passwordEncoder.encode(coworker.getPassword()));
        return REPOSITORY.save(coworker);
    }

    public Coworker getCoworker(long id) {
        return REPOSITORY.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new NotFoundException("Coworker not found."));
    }

    public List<Coworker> getCoworkers() {
        return REPOSITORY.findAllByisActiveTrue();
    }

    public Coworker updateCoworker(long id, Coworker coworkerUpdate) {
        Coworker coworker = REPOSITORY.findById(id)
                .orElseThrow(() -> new NotFoundException("Coworker not found."));

        if (coworkerUpdate.getFirstName() != null) coworker.setFirstName(coworkerUpdate.getFirstName());
        if (coworkerUpdate.getLastName() != null) coworker.setLastName(coworkerUpdate.getLastName());
        if (coworkerUpdate.getEmail() != null) coworker.setEmail(coworkerUpdate.getEmail());
        if (coworkerUpdate.getPhone() != null) coworker.setPhone(coworkerUpdate.getPhone());
        if (coworkerUpdate.getPermission() != null) {
            validatePermissionGroup(coworkerUpdate);
            validateAdminCreation(coworkerUpdate);
            coworker.setPermission(permissionGroupService.permissionGroupById(coworkerUpdate.getPermission().getId()));
        }

        return REPOSITORY.save(coworker);
    }

    public void deleteCoworker(long id) {
        String loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Coworker coworker = REPOSITORY.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new NotFoundException("Coworker not found."));

        if(loggedUserEmail.equalsIgnoreCase(coworker.getEmail()))
            throw new ConflictException("You cannot delete your own account.");

        coworker.setIsActive(false);
        REPOSITORY.save(coworker);
    }

    public void validateConflict(Coworker coworker) {
        if(REPOSITORY.existsByEmail(coworker.getEmail())) throw new ConflictException("Email already exists");
        if(REPOSITORY.existsByPhone(coworker.getPhone())) throw new ConflictException("Phone already exists.");
    }

    public void validateConflictUpdate(Long id, Coworker coworker) {
        REPOSITORY.findByEmailAndIsActiveTrue(coworker.getEmail()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ConflictException("Email already exists for another user.");
            }
        });

        REPOSITORY.findByPhoneAndIsActiveTrue(coworker.getPhone()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ConflictException("Phone already exists for another user.");
            }
        });
    }

    public void validatePermissionGroup(Coworker coworker) {
        if (coworker.getPermission() == null || coworker.getPermission().getId() == null) {
            throw new NotFoundException("Permission group is required for coworker.");
        }

        permissionGroupService.permissionGroupById(coworker.getPermission().getId());
    }

    private boolean isUserAdmin() {
        String loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Coworker loggedUser = REPOSITORY.findByEmailAndIsActiveTrue(loggedUserEmail)
                .orElseThrow(() -> new NotFoundException("Logged user not found."));

        return loggedUser.getPermission() != null && loggedUser.getPermission().getId() == 1L;
    }

    public void validateAdminCreation(Coworker coworker) {
        if(coworker.getPermission() != null && coworker.getPermission().getId() == 1L) {
            if(!isUserAdmin()) {
                throw new ConflictException("Only admins can create or assign admin permission.");
            }
        }
    }

}
