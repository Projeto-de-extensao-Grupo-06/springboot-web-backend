package com.solarize.solarize_web_backend.modules.auth;

import com.solarize.solarize_web_backend.modules.auth.dtos.UserDetailsDto;
import com.solarize.solarize_web_backend.modules.user.User;
import com.solarize.solarize_web_backend.modules.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        Optional<User> usuarioOpt = userRepository.findByEmail(username);

        if (usuarioOpt.isEmpty()){

            throw new UsernameNotFoundException(String.format("usuario: %s não encontrado", username));

        }

        return new UserDetailsDto(usuarioOpt.get());

    }

}
