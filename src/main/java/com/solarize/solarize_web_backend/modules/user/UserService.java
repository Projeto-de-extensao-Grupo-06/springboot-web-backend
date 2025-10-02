package com.solarize.solarize_web_backend.modules.user;

import com.solarize.solarize_web_backend.shared.security.JwtTokenManager;
import com.solarize.solarize_web_backend.modules.user.dtos.UserResponseDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtTokenManager jwtTokenManager, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void criar(User novoUser){

        String hashedPassword = passwordEncoder.encode(novoUser.getPassword());
        novoUser.setPassword(hashedPassword);

        this.userRepository.save(novoUser);

    }


    public List<UserResponseDto> listarTodos(){

        List<User> users = userRepository.findAll();

        return users.stream().map(UserMapper::of).toList();

    }


}
