package com.solarize.solarize_web_backend.shared.security;

import com.solarize.solarize_web_backend.modules.auth.AuthService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthProvider implements AuthenticationProvider {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    public AuthProvider(AuthService authService, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Authentication authenticate(final Authentication authentication) throws ArithmeticException{

        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();

        UserDetails userDetails = this.authService.loadUserByUsername(username);

        if (this.passwordEncoder.matches(password, userDetails.getPassword())){
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }else {
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }
    }

    @Override
    public boolean supports(final  Class<?> authentication){
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
