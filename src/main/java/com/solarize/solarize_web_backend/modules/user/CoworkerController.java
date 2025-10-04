package com.solarize.solarize_web_backend.modules.user;

import com.solarize.solarize_web_backend.modules.user.dtos.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/users")
public class CoworkerController {
    private final CoworkerService coworkerService;

    public CoworkerController(CoworkerService coworkerService) {
        this.coworkerService = coworkerService;
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> criar(@RequestBody @Valid CoworkerCreateDto coworkerCreateDto){
        final Coworker newCoworker = CoworkerMapper.of(coworkerCreateDto);
        this.coworkerService.criar(newCoworker);
        return ResponseEntity.status(201).build();

    }
    
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<CoworkerResponseDto>> listarTodos(){

        List<CoworkerResponseDto> usuariosEncontrados  = this.coworkerService.listarTodos();

        if (usuariosEncontrados.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(usuariosEncontrados);

    }

}
