package com.solarize.solarizeWebBackend.modules.auth;

import com.solarize.solarizeWebBackend.modules.auth.dtos.CoworkerDetailsDto;
import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.coworker.CoworkerRepository;
import com.solarize.solarizeWebBackend.modules.auth.dtos.AuthResponseDto;
import com.solarize.solarizeWebBackend.shared.security.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final CoworkerRepository coworkerRepository;
    private final JwtTokenManager jwtTokenManager;
    private final AuthenticationConfiguration authenticationManager;

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

            return AuthMapper.of(coworkerAuthenticated, token, authentication.getAuthorities().stream().collect(Collectors.toList()));
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid Credentials");
        }
    }
}
