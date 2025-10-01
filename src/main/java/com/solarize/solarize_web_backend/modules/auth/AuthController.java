package com.solarize.solarize_web_backend.modules.auth;

import com.solarize.solarize_web_backend.modules.user.User;
import com.solarize.solarize_web_backend.modules.user.dtos.AuthResponseDto;
import com.solarize.solarize_web_backend.modules.user.dtos.UserCredentialsDto;
import com.solarize.solarize_web_backend.modules.user.dtos.UserMapper;
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
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserCredentialsDto userCredentialsDto){
        final User user = UserMapper.of(userCredentialsDto);
        AuthResponseDto authResponseDto = this.authService.login(user);

        return ResponseEntity.status(200).body(authResponseDto);
    }
}
