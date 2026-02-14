package com.solarize.solarizeWebBackend.modules.project.dto.response;

import com.solarize.solarizeWebBackend.modules.project.ProjectSourceEnum;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
public class LeadResponseDTO {
    private Long id;
    private String clientName;
    private ProjectSourceEnum projectFrom;
    private LocalDateTime createdAt;
    private ProjectStatusEnum status;
}
