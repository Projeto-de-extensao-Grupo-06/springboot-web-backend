package com.solarize.solarize_web_backend.modules.user;

import com.solarize.solarize_web_backend.shared.security.JwtTokenManager;
import com.solarize.solarize_web_backend.modules.user.dtos.UserResponseDto;
import com.solarize.solarize_web_backend.modules.user.dtos.UserMapper;
import com.solarize.solarize_web_backend.modules.user.dtos.AuthResponseDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenManager jwtTokenManager;
    private final AuthenticationManager authenticationManager;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtTokenManager jwtTokenManager, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.authenticationManager = authenticationManager;
    }

    public void criar(User novoUser){

        String hashedPassword = passwordEncoder.encode(novoUser.getPassword());
        novoUser.setPassword(hashedPassword);

        this.userRepository.save(novoUser);

    }

    public AuthResponseDto autenticar(User user){

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        User userAuthenticated = userRepository.findByEmail(user.getEmail()).orElseThrow(()-> new ResponseStatusException(404, "Email do usuário não cadastrado", null));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token =  jwtTokenManager.generateToken(authentication, userAuthenticated.getId());

        return UserMapper.of(userAuthenticated, token);

    }

    public List<UserResponseDto> listarTodos(){

        List<User> users = userRepository.findAll();

        return users.stream().map(UserMapper::of).toList();

    }


}
