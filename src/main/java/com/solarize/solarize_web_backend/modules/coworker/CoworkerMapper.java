package com.solarize.solarize_web_backend.modules.coworker;


import com.solarize.solarize_web_backend.modules.coworker.dtos.CoworkerCreateDto;
import com.solarize.solarize_web_backend.modules.coworker.dtos.CoworkerResponseDto;

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

}
