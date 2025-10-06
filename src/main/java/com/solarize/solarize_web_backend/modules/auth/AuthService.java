package com.solarize.solarize_web_backend.modules.auth;

import com.solarize.solarize_web_backend.modules.auth.dtos.CoworkerDetailsDto;
import com.solarize.solarize_web_backend.modules.coworker.Coworker;
import com.solarize.solarize_web_backend.modules.coworker.CoworkerRepository;
import com.solarize.solarize_web_backend.modules.auth.dtos.AuthResponseDto;
import com.solarize.solarize_web_backend.shared.security.JwtTokenManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {
    private final CoworkerRepository coworkerRepository;
    private final JwtTokenManager jwtTokenManager;
    private final AuthenticationConfiguration authenticationManager;

    public AuthService(CoworkerRepository coworkerRepository, JwtTokenManager jwtTokenManager, AuthenticationConfiguration authenticationConfiguration) {
        this.coworkerRepository = coworkerRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.authenticationManager = authenticationConfiguration;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Coworker> usuarioOpt = coworkerRepository.findByEmailWithPermissions(username);

        if (usuarioOpt.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }

        return new CoworkerDetailsDto(usuarioOpt.get());
    }


    public AuthResponseDto login(Coworker coworker){

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(coworker.getEmail(), coworker.getPassword());

        try {
            final Authentication authentication = this.authenticationManager.getAuthenticationManager().authenticate(credentials);

            Coworker coworkerAuthenticated = coworkerRepository.findByEmail(coworker.getEmail()).orElseThrow(()-> new ResponseStatusException(404, "Email do usuário não cadastrado", null));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            final String token =  jwtTokenManager.generateToken(authentication, coworkerAuthenticated.getId());

            return AuthMapper.of(coworkerAuthenticated, token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
