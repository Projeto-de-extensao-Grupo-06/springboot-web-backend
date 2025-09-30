package com.solarize.solarize_web_backend.modules.user;

import com.solarize.solarize_web_backend.modules.user.dtos.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> criar(@RequestBody @Valid UserCreateDto userCreateDto){
        final User newUser = UserMapper.of(userCreateDto);
        this.userService.criar(newUser);
        return ResponseEntity.status(201).build();

    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<UserResponseDto>> listarTodos(){

        List<UserResponseDto> usuariosEncontrados  = this.userService.listarTodos();

        if (usuariosEncontrados.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(usuariosEncontrados);

    }

}
