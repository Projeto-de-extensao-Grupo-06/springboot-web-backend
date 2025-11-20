package com.solarize.solarizeWebBackend.modules.project.projectListener.event;

import com.solarize.solarizeWebBackend.modules.project.Project;
import lombok.AllArgsConstructor;

public record ProjectStatusChangedToRetryingEvent(Project project) {}
