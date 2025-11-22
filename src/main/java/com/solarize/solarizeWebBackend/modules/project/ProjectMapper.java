package com.solarize.solarizeWebBackend.modules.project;


import com.solarize.solarizeWebBackend.modules.client.ClientMapper;
import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.coworker.CoworkerMapper;
import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerResponseDto;
import com.solarize.solarizeWebBackend.modules.project.dto.ProjectSummaryDTO;

import java.util.ArrayList;
import java.util.List;

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
}
