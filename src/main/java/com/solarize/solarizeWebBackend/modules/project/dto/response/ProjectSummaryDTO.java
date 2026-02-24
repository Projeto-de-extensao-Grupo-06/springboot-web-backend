package com.solarize.solarizeWebBackend.modules.project.dto.response;

import com.solarize.solarizeWebBackend.modules.client.dto.ClientResponseDTO;
import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerResponseDto;
import com.solarize.solarizeWebBackend.modules.project.SystemTypeEnum;
import lombok.*;

import java.time.LocalDateTime;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectSummaryDTO {
    private Long id;
    private String projectTitle;
    private String status;
    private LocalDateTime nextSchedule;
    private CoworkerResponseDto responsible;
    private ClientResponseDTO client;
    private LocalDateTime deadline;
    private SystemTypeEnum systemType;
    private Integer commentCount;
    private Integer fileCount;
    private LocalDateTime createdAt;
}
