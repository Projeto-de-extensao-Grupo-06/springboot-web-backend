package com.solarize.solarizeWebBackend.modules.auth;

import com.solarize.solarizeWebBackend.modules.auth.dtos.AuthResponseDto;
import com.solarize.solarizeWebBackend.shared.security.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;

@RestController
@RequestMapping("/auth/bot")
@RequiredArgsConstructor
public class BotAuthController {

    private final JwtTokenManager jwtTokenManager;

    @Value("${solarize.security.bot-secret}")
    private String botSecret;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> loginBot(@RequestHeader("x-bot-secret") String headerSecret) {
        
        if (botSecret == null || botSecret.isBlank() || !botSecret.equals(headerSecret)) {
            throw new BadCredentialsException("Invalid bot secret");
        }

        List<String> authorities = List.of("CLIENT_WRITE", "CLIENT_READ", "PROJECT_WRITE", "PROJECT_READ", "ADDRESS_WRITE");

        String token = jwtTokenManager.generateBotToken("BOT_USER", authorities);

        AuthResponseDto response = AuthResponseDto.builder()
                .token(token)
                .firstName("Bot")
                .lastName("WhatsApp")
                .authorities(authorities.stream().map(SimpleGrantedAuthority::new).toList())
                .build();

        return ResponseEntity.ok(response);
    }
}
