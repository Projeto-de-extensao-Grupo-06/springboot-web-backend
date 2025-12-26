package com.solarize.solarizeWebBackend.modules.coworker;

import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerCreateDto;
import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerResponseDto;
import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerUpdateDto;

import java.util.List;
import java.util.stream.Collectors;

public class CoworkerMapper {
    public static Coworker toEntity(CoworkerCreateDto dto){
        if(dto == null) return null;

        Coworker coworker = new Coworker();
        coworker.setFirstName(dto.getFirstName());
        coworker.setLastName(dto.getLastName());
        coworker.setPhone(dto.getPhone());
        coworker.setEmail(dto.getEmail());
        coworker.setPassword(dto.getPassword());

        return coworker;
    }

    public static Coworker toEntity(CoworkerUpdateDto dto){
        if(dto == null) return null;

        Coworker coworker = new Coworker();
        coworker.setFirstName(dto.getFirstName());
        coworker.setLastName(dto.getLastName());
        coworker.setPhone(dto.getPhone());
        coworker.setEmail(dto.getEmail());

        return coworker;
    }

    public static CoworkerResponseDto toDto(Coworker coworker) {
        if(coworker == null) return null;

        return CoworkerResponseDto.builder()
                .id(coworker.getId())
                .firstName(coworker.getFirstName())
                .lastName(coworker.getLastName())
                .email(coworker.getEmail())
                .phone(coworker.getPhone())
                .build();
    }

    public static List<CoworkerResponseDto> toDtoList(List<Coworker> coworkers) {
        if(coworkers == null) return null;

        return coworkers.stream()
                .map(CoworkerMapper::toDto)
                .collect(Collectors.toList());
    }

    public static void updateEntity(Coworker coworker, CoworkerCreateDto dto) {
        if(dto == null || coworker == null) return;
        if(dto.getEmail() != null) coworker.setEmail(dto.getEmail());
        if(dto.getFirstName() != null) coworker.setFirstName(dto.getFirstName());
        if(dto.getLastName() != null) coworker.setLastName(dto.getLastName());
        if(dto.getPhone() != null) coworker.setPhone(dto.getPhone());
    }

}
