package com.solarize.solarizeWebBackend.modules.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectBotCreatedDto {
    private Long projectId;
    private Long budgetId;
}
