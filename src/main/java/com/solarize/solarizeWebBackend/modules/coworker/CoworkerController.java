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
    public ResponseEntity<CoworkerResponseDto> getCoworker(@PathVariable long id) {
        Coworker entity = SERVICE.getCoworker(id);
        final CoworkerResponseDto dto = CoworkerMapper.toDto(entity);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<List<CoworkerResponseDto>> getCoworkers() {
        final List<Coworker> entities = SERVICE.getCoworkers();
        final List<CoworkerResponseDto> dtos = CoworkerMapper.toDtoList(entities);
        if(dtos.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CoworkerResponseDto> postCoworker(@Valid @RequestBody CoworkerCreateDto dto) {
        Coworker entity = CoworkerMapper.toEntity(dto);
        Coworker createdCoworker = SERVICE.createCoworker(entity);
        final CoworkerResponseDto responseDto = CoworkerMapper.toDto(createdCoworker);
        return ResponseEntity.status(201).body(responseDto);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CoworkerResponseDto> putCoworker(@PathVariable long id, @Valid @RequestBody CoworkerCreateDto dto) {

        Coworker updatedData = CoworkerMapper.toEntity(dto);
        SERVICE.validateConflictUpdate(id,updatedData);
        Coworker updated = SERVICE.updateCoworker(id,updatedData);
        final CoworkerResponseDto responseDto = CoworkerMapper.toDto(updated);
        return ResponseEntity.status(200).body(responseDto);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoworker(@PathVariable long id) {
        SERVICE.deleteCoworker(id);
        return ResponseEntity.status(204).build();
    }
}
