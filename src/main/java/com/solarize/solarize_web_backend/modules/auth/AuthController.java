package com.solarize.solarize_web_backend.modules.auth;

import com.solarize.solarize_web_backend.modules.user.User;
import com.solarize.solarize_web_backend.modules.auth.dtos.AuthResponseDto;
import com.solarize.solarize_web_backend.modules.user.dtos.UserCredentialsDto;
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

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @RequestBody UserCredentialsDto userCredentialsDto,
            HttpServletResponse response
    ){
        final User user = AuthMapper.of(userCredentialsDto);
        AuthResponseDto authResponseDto = this.authService.login(user);

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
