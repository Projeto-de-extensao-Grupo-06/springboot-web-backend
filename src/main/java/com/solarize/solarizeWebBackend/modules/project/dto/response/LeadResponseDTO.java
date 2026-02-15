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
    private Long projectId;
    private String clientName;
    private String clientPhone;
    private ProjectSourceEnum projectFrom;
    private LocalDateTime createdAt;
    private ProjectStatusEnum status;
}
