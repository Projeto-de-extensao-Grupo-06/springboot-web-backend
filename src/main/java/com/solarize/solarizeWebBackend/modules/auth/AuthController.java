package com.solarize.solarizeWebBackend.modules.auth;

import com.solarize.solarizeWebBackend.modules.auth.dtos.RecoveryPasswordDto;
import com.solarize.solarizeWebBackend.modules.auth.dtos.RecoveryPasswordOtpDto;
import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.auth.dtos.AuthResponseDto;
import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerCredentialsDto;
import com.solarize.solarizeWebBackend.shared.globalExceptionHandler.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua_parser.Client;
import ua_parser.Parser;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

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


        Cookie authoritiesCookie = new Cookie("userAuthorities", Base64.getEncoder().encodeToString(authResponseDto
                .getAuthorities()
                .toString()
                .getBytes(StandardCharsets.UTF_8)));
        authoritiesCookie.setPath("/");
        authoritiesCookie.setMaxAge(60 * 60 * 6);
        authoritiesCookie.setAttribute("SameSite", "Strict");
        response.addCookie(authoritiesCookie);

        return ResponseEntity.status(200).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response){
        Cookie authCookie = new Cookie("Authorization", "");
        authCookie.setHttpOnly(true);
        authCookie.setPath("/");
        authCookie.setMaxAge(0);
        response.addCookie(authCookie);

        Cookie firstNameCookie = new Cookie("userFirstName", "");
        firstNameCookie.setPath("/");
        firstNameCookie.setMaxAge(0);
        response.addCookie(firstNameCookie);

        Cookie lastNameCookie = new Cookie("userLastName", "");
        lastNameCookie.setPath("/");
        lastNameCookie.setMaxAge(0);
        response.addCookie(lastNameCookie);

        Cookie authoritiesCookie = new Cookie("userAuthorities", "");
        authoritiesCookie.setPath("/");
        authoritiesCookie.setMaxAge(0);
        response.addCookie(authoritiesCookie);

        return  ResponseEntity.status(200).build();
    }


    @PostMapping("/forget-password")
    public ResponseEntity<Void> requestRecoveryPasswordCode(HttpServletRequest request, @RequestBody @Valid RecoveryPasswordDto body) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        String userAgent = request.getHeader("User-Agent");

        if(ipAddress == null || ipAddress.isBlank()) {
            ipAddress = request.getRemoteAddr();
        }

        Parser headerParser = new Parser();
        Client client = headerParser.parse(userAgent);

        this.authService.requestRecoveryPasswordCode(
                body.getEmail(),
                client.userAgent.family,
                client.os.family + " " + client.os.major,
                ipAddress
        );

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/confirm-otp")
    public ResponseEntity<Void> confirmOtpCode(@RequestBody @Valid RecoveryPasswordOtpDto body, HttpServletResponse response) {
        String resetPassToken = this.authService.confirmOtpCode(body.getEmail(), body.getOtp());

        Cookie cookie = new Cookie("reset-pass-token", resetPassToken);
        cookie.setMaxAge(60 * 15);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.noContent().build();
    }

}
