package com.solarize.solarizeWebBackend.modules.project.dto.response;

import com.solarize.solarizeWebBackend.modules.address.Address;
import com.solarize.solarizeWebBackend.modules.address.dto.ResponseAddressDto;
import com.solarize.solarizeWebBackend.modules.budget.model.Budget;
import com.solarize.solarizeWebBackend.modules.client.Client;
import com.solarize.solarizeWebBackend.modules.client.dto.ClientResponseDTO;
import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerResponseDto;
import com.solarize.solarizeWebBackend.modules.project.ProjectSourceEnum;
import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import com.solarize.solarizeWebBackend.modules.project.SystemTypeEnum;
import com.solarize.solarizeWebBackend.modules.projectFile.ProjectFile;
import com.solarize.solarizeWebBackend.modules.retryQueue.RetryQueue;
import com.solarize.solarizeWebBackend.modules.schedule.Schedule;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ProjectDto {
    private Long id;
    private String name;
    private String description;

    private ProjectStatusEnum status;
    private ProjectStatusEnum previewStatus;
    private Boolean isActive;

    private Long clientId;
    private Long coworkerId;
    private Long addressId;

    private LocalDateTime createdAt;
    private SystemTypeEnum systemType;
    private ProjectSourceEnum projectFrom;
}
