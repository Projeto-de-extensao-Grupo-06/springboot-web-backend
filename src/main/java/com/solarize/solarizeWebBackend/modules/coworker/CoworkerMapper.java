package com.solarize.solarizeWebBackend.modules.coworker;

import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerCreateDto;
import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerResponseDto;

import java.util.ArrayList;
import java.util.List;

public class CoworkerMapper {
    public static Coworker of(CoworkerCreateDto coworkerCreateDto){

        Coworker coworker = new Coworker();

        coworker.setFirstName(coworkerCreateDto.getFirstName());
        coworker.setLastName(coworkerCreateDto.getLastName());
        coworker.setPhone(coworkerCreateDto.getPhone());
        coworker.setEmail(coworkerCreateDto.getEmail());
        coworker.setPassword(coworkerCreateDto.getPassword());

        return coworker;
    }

    public static CoworkerResponseDto of(Coworker coworker){
        CoworkerResponseDto coworkerResponseDto = new CoworkerResponseDto();

        coworkerResponseDto.setId(coworker.getId());
        coworkerResponseDto.setEmail(coworker.getEmail());
        coworkerResponseDto.setFirstName(coworker.getFirstName());
        coworkerResponseDto.setLastName(coworker.getLastName());
        coworkerResponseDto.setPhone(coworker.getPhone());

        return coworkerResponseDto;
    }

    public static List<CoworkerResponseDto> of(List<Coworker> coworkers) {
        if(coworkers == null) return null;

        List<CoworkerResponseDto> coworkerDTO = new ArrayList<>();

        coworkers.forEach(coworker -> {
            coworkerDTO.add(
                    CoworkerResponseDto.builder()
                            .id(coworker.getId())
                            .firstName(coworker.getFirstName())
                            .lastName(coworker.getLastName())
                            .email(coworker.getEmail())
                            .phone(coworker.getPhone())
                            .build()
            );
        });
        return coworkerDTO;
    }

    public static void updateEntity(Coworker coworker, CoworkerCreateDto dto) {
        if(dto.getEmail() != null) coworker.setEmail(dto.getEmail());
        if(dto.getFirstName() != null) coworker.setFirstName(dto.getFirstName());
        if(dto.getLastName() != null) coworker.setLastName(dto.getLastName());
        if(dto.getPhone() != null) coworker.setPhone(dto.getPhone());
    }

}
