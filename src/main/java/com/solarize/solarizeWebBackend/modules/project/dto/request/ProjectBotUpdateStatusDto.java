package com.solarize.solarizeWebBackend.modules.project.dto.request;

import com.solarize.solarizeWebBackend.modules.project.ProjectStatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectBotUpdateStatusDto {
    @NotNull(message = "Status is required")
    private ProjectStatusEnum status;
}
