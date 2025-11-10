package com.solarize.solarizeWebBackend.modules.project.DTO;

import com.solarize.solarizeWebBackend.modules.client.dto.ClientResponseDTO;
import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Builder
@Getter
public class ProjectSummaryDTO {

    private Long id;
    private String projectTitle;
    private String status;
    private LocalDateTime nextSchedule;
    private CoworkerResponseDto responsible;
    private ClientResponseDTO client;
    private Long commentCount;
    private Long fileCount;

}
