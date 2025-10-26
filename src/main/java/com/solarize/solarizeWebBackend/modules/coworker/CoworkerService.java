package com.solarize.solarizeWebBackend.modules.coworker;

import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerCreateDto;
import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerResponseDto;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoworkerService {
    private final PasswordEncoder passwordEncoder;
    private final CoworkerRepository REPOSITORY;

    public CoworkerResponseDto getCoworker(long id) {
        Optional<Coworker> coworker = REPOSITORY.findById(id);
        if(coworker.isEmpty()) throw new NotFoundException("Coworker not found.");
        return CoworkerMapper.of(coworker.get());
    }

    public List<CoworkerResponseDto> getCoworkers() {
        List<Coworker> coworkers = REPOSITORY.findAll();
        return CoworkerMapper.of(coworkers);
    }

    public CoworkerResponseDto createCoworker(CoworkerCreateDto dto) {
        Coworker coworker = CoworkerMapper.of(dto);
        String hashedPassword = passwordEncoder.encode(coworker.getPassword());
        coworker.setPassword(hashedPassword);
        coworker = REPOSITORY.save(coworker);
        return CoworkerMapper.of(coworker);
    }

    public CoworkerResponseDto updateCoworker(long id, CoworkerCreateDto dto) {
        Coworker coworker = REPOSITORY.findById(id)
                .orElseThrow(() -> new NotFoundException("Coworker not found."));

        CoworkerMapper.updateEntity(coworker, dto);

        if (dto.getPassword() != null) {
            coworker.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        coworker = REPOSITORY.save(coworker);
        return CoworkerMapper.of(coworker);
    }

    public void deleteCoworker(long id) {
        Coworker coworker = REPOSITORY.findById(id)
                .orElseThrow(() -> new NotFoundException("Coworker not found."));

        REPOSITORY.delete(coworker);
    }
}
