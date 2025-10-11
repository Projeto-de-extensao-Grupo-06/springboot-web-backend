package com.solarize.solarizeWebBackend.modules.coworker;

import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerResponseDto;
import lombok.RequiredArgsConstructor;
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
