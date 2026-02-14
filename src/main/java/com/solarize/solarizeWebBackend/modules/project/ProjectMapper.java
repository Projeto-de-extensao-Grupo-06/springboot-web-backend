package com.solarize.solarizeWebBackend.modules.project;


import com.solarize.solarizeWebBackend.modules.address.Address;
import com.solarize.solarizeWebBackend.modules.address.AddressMapper;
import com.solarize.solarizeWebBackend.modules.address.dto.ResponseAddressDto;
import com.solarize.solarizeWebBackend.modules.client.Client;
import com.solarize.solarizeWebBackend.modules.client.ClientMapper;
import com.solarize.solarizeWebBackend.modules.client.dto.ClientResponseDTO;
import com.solarize.solarizeWebBackend.modules.coworker.CoworkerMapper;
import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerResponseDto;
import com.solarize.solarizeWebBackend.modules.project.dto.request.ProjectManualCreateDto;
import com.solarize.solarizeWebBackend.modules.project.dto.request.ProjectUpdateDto;
import com.solarize.solarizeWebBackend.modules.project.dto.response.LeadResponseDTO;
import com.solarize.solarizeWebBackend.modules.project.dto.response.ProjectDto;
import com.solarize.solarizeWebBackend.modules.project.dto.response.ProjectSummaryDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectMapper {
    public static ProjectSummaryDTO toSummary(Project entity) {
        if (entity == null) return null;

        return ProjectSummaryDTO.builder()
                .id(entity.getId())
                .projectTitle(entity.getName())
                .status(entity.getStatus().name())
                .responsible(CoworkerMapper.toDto(entity.getResponsible()))
                .client(ClientMapper.of(entity.getClient()))
                .build();
    }


    public static Project toEntity(ProjectManualCreateDto dto) {
        if(dto == null) return null;

        Project project = new Project();
        Client client = new Client();
        Address address = new Address();

        client.setId(dto.getClientId());
        address.setId(dto.getAddressId());

        project.setName(dto.getName());
        project.setClient(client);
        project.setDescription(dto.getDescription());
        project.setAddress(address);
        project.setSystemType(dto.getProjectType());

        return project;
    }


    public static Project toEntity(ProjectUpdateDto dto) {
        if(dto == null) return null;

        Project project = new Project();
        Client client = new Client();

        client.setId(dto.getClientId());
        project.setName(dto.getName());
        project.setClient(client);
        project.setDescription(dto.getDescription());
        project.setSystemType(dto.getProjectType());

        return project;
    }

    public static ProjectDto toDto(Project entity) {
        if(entity == null) return null;

        return ProjectDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .isActive(entity.getIsActive())
                .status(entity.getStatus())
                .previewStatus(entity.getPreviewStatus())
                .projectFrom(entity.getProjectFrom())
                .createdAt(entity.getCreatedAt())
                .systemType(entity.getSystemType())
                .coworkerId(entity.getResponsible().getId())
                .clientId(entity.getClient().getId())
                .addressId(entity.getAddress().getId())
                .build();
    }

    public static List<ProjectSummaryDTO> toSummary(List<Project> entities){
        if (entities == null) return null;

        return entities.stream()
                .map(ProjectMapper::toSummary)
                .collect(Collectors.toList());
    }

    public static LeadResponseDTO of(Project entity){
        if (entity == null) return null;

        return LeadResponseDTO.builder()
                .id(entity.getId())
                .clientName(String.join(
                        " ",
                        entity.getClient().getFirstName(),
                        entity.getClient().getLastName()
                ))
                .projectFrom(entity.getProjectFrom())
                .createdAt(entity.getCreatedAt())
                .status(entity.getStatus())
                .build();
    }

    public static List<LeadResponseDTO> of(List<Project> entities){
        if (entities == null) return null;

        return entities.stream()
                .map(ProjectMapper::of)
                .collect(Collectors.toList());
    }
}
