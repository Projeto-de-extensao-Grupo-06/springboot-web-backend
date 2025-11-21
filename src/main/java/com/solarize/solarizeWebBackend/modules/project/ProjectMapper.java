package com.solarize.solarizeWebBackend.modules.project;

import com.solarize.solarizeWebBackend.modules.client.Client;
import com.solarize.solarizeWebBackend.modules.client.dto.ClientResponseDTO;
import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerResponseDto;
import com.solarize.solarizeWebBackend.modules.project.dto.ProjectSummaryDTO;

import java.util.ArrayList;
import java.util.List;

public class ProjectMapper {


    public static ProjectSummaryDTO toSummary(Project entidade) {
        if (entidade == null) return null;

        return ProjectSummaryDTO.builder()
                .id(entidade.getId())
                .projectTitle(entidade.getName())
                .status(entidade.getStatus().name())
                .responsible(toCoworkerDto(entidade.getResponsible()))
                .client(toClientDto(entidade.getClient()))
                .build();
    }

    private static ClientResponseDTO toClientDto(Client entidade) {
        if (entidade == null) return null;

        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setId(entidade.getId());
        dto.setFirstName(entidade.getFirstName());
        dto.setLastName(entidade.getLastName());
        dto.setEmail(entidade.getEmail());
        dto.setPhone(entidade.getPhone());
        return dto;
    }

    private static CoworkerResponseDto toCoworkerDto(Coworker entidade) {
        if (entidade == null) return null;

        CoworkerResponseDto dto = new CoworkerResponseDto();
        dto.setId(entidade.getId());
        dto.setFirstName(entidade.getFirstName());
        dto.setLastName(entidade.getLastName());
        dto.setEmail(entidade.getEmail());
        dto.setPhone(entidade.getPhone());
        return dto;
    }

}
