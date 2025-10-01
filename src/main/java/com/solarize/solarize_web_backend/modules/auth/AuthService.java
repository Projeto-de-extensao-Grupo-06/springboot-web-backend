package com.solarize.solarize_web_backend.modules.auth;

import com.solarize.solarize_web_backend.modules.auth.dtos.UserDetailsDto;
import com.solarize.solarize_web_backend.modules.user.User;
import com.solarize.solarize_web_backend.modules.user.UserRepository;
import com.solarize.solarize_web_backend.modules.user.dtos.AuthResponseDto;
import com.solarize.solarize_web_backend.modules.user.dtos.UserMapper;
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
    private final UserRepository userRepository;
    private final JwtTokenManager jwtTokenManager;
    private final AuthenticationConfiguration authenticationManager;

    public AuthService(UserRepository userRepository, JwtTokenManager jwtTokenManager, AuthenticationConfiguration authenticationConfiguration) {
        this.userRepository = userRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.authenticationManager = authenticationConfiguration;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<User> usuarioOpt = userRepository.findByEmail(username);

        if (usuarioOpt.isEmpty()){
            throw new UsernameNotFoundException(String.format("usuario: %s não encontrado", username));
        }

        return new UserDetailsDto(usuarioOpt.get());
    }


    public AuthResponseDto login(User user){

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        try {
            final Authentication authentication = this.authenticationManager.getAuthenticationManager().authenticate(credentials);

            User userAuthenticated = userRepository.findByEmail(user.getEmail()).orElseThrow(()-> new ResponseStatusException(404, "Email do usuário não cadastrado", null));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            final String token =  jwtTokenManager.generateToken(authentication, userAuthenticated.getId());

            return UserMapper.of(userAuthenticated, token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
