package com.solarize.solarizeWebBackend.modules.coworker;

import com.solarize.solarizeWebBackend.modules.coworker.dtos.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/coworkers")
@RequiredArgsConstructor
public class CoworkerController {
    private final CoworkerService SERVICE;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CoworkerResponseDto> getCoworker(
            @PathVariable long id
    ) {
        final CoworkerResponseDto coworker = SERVICE.getCoworker(id);
        return ResponseEntity.ok(coworker);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<List<CoworkerResponseDto>> getCoworkers() {
        final List<CoworkerResponseDto> coworkers = SERVICE.getCoworkers();

        if(coworkers.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.ok(coworkers);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CoworkerResponseDto> postCoworker(
        @Valid @RequestBody CoworkerCreateDto coworker
    ) {
        final CoworkerResponseDto createdCoworker = SERVICE.createCoworker(coworker);
        return ResponseEntity.status(201).body(createdCoworker);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CoworkerResponseDto> putCoworker(@PathVariable long id,
            @Valid @RequestBody CoworkerCreateDto coworker
    ) {
        final CoworkerResponseDto updatedCoworker = SERVICE.updateCoworker(id,coworker);
        return ResponseEntity.status(200).body(updatedCoworker);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoworker(@PathVariable long id) {
        SERVICE.deleteCoworker(id);
        return ResponseEntity.status(204).build();
    }
}
