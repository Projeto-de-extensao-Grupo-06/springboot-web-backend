package com.solarize.solarizeWebBackend.modules.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectKpiDto implements Serializable {
    private Long upcomingDeadlines;
    private Long awaitingContact;
    private Long recentProjects;
    private Long stagnantProjects;
}
