package com.solarize.solarizeWebBackend.modules.project.dto;

import com.solarize.solarizeWebBackend.modules.project.Project;

public class ProjectMapper {

    public static ProjectResponseDTO toDto(Project project){
        if (project == null) return null;

        return ProjectResponseDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .projectStatusEnum(project.getStatus())
                .statusWeight(project.getStatusWeight())
                .isActive(project.getIsActive())
                .systemTypeEnum(project.getSystemType())
                .projectSourceEnum(project.getProjectFrom())
                .clientId(project.getClient().getId() != null ? project.getClient().getId() : null)
                .addressId(project.getAddress() != null ? project.getAddress().getId() : null)
                .budgetId(project.getBudget() != null ? project.getBudget().getId() : null)
                .createdAt(project.getCreatedAt())
                .deadline(project.getDeadline())
                .build();
    }


    public static Project toEntity(CreateProjectRequestDTO createProjectRequestDTO){

        if (createProjectRequestDTO == null) return null;

        Project project = new Project();
        project.setName(createProjectRequestDTO.getName());
        project.setDescription(createProjectRequestDTO.getDescription());
        project.setSystemType(createProjectRequestDTO.getSystemTypeEnum());

        return project;

    }

}
