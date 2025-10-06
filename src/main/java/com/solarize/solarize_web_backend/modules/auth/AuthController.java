package com.solarize.solarize_web_backend.modules.auth;

import com.solarize.solarize_web_backend.modules.coworker.Coworker;
import com.solarize.solarize_web_backend.modules.auth.dtos.AuthResponseDto;
import com.solarize.solarize_web_backend.modules.coworker.dtos.CoworkerCredentialsDto;
import com.solarize.solarize_web_backend.shared.globalExceptionHandler.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @Operation(summary = "Login de usuário", description = "Autentica um usuário e retorna token JWT")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = AuthResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Credenciais incorretas",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                     content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @RequestBody CoworkerCredentialsDto coworkerCredentialsDto,
            HttpServletResponse response
    ){
        final Coworker coworker = AuthMapper.of(coworkerCredentialsDto);
        AuthResponseDto authResponseDto = this.authService.login(coworker);

        Cookie tokenCookie = new Cookie("Authorization", authResponseDto.getToken());
        tokenCookie.setPath("/");
        tokenCookie.setHttpOnly(true);
        tokenCookie.setAttribute("SameSite", "Strict");
        tokenCookie.setMaxAge(60 * 60 * 6);
        response.addCookie(tokenCookie);

        Cookie userFirstNameCookie = new Cookie("userFirstName", authResponseDto.getFirstName());
        userFirstNameCookie.setPath("/");
        userFirstNameCookie.setMaxAge(60 * 60 * 6);
        userFirstNameCookie.setAttribute("SameSite", "Strict");
        response.addCookie(userFirstNameCookie);

        Cookie userLastNameCookie = new Cookie("userLastName", authResponseDto.getLastName());
        userLastNameCookie.setPath("/");
        userLastNameCookie.setMaxAge(60 * 60 * 6);
        userLastNameCookie.setAttribute("SameSite", "Strict");
        response.addCookie(userLastNameCookie);

        return ResponseEntity.status(200).body(authResponseDto);
    }
}
