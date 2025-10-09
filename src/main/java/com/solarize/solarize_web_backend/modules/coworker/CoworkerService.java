package com.solarize.solarize_web_backend.modules.coworker;

import com.solarize.solarize_web_backend.shared.security.JwtTokenManager;
import com.solarize.solarize_web_backend.modules.coworker.dtos.CoworkerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoworkerService {
    private final PasswordEncoder passwordEncoder;
    private final CoworkerRepository coworkerRepository;

    public void criar(Coworker novoCoworker){

        String hashedPassword = passwordEncoder.encode(novoCoworker.getPassword());
        novoCoworker.setPassword(hashedPassword);

        this.coworkerRepository.save(novoCoworker);

    }


    public List<CoworkerResponseDto> listarTodos(){

        List<Coworker> coworkers = coworkerRepository.findAll();

        return coworkers.stream().map(CoworkerMapper::of).toList();

    }


}
