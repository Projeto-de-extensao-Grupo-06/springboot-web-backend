package com.solarize.solarizeWebBackend.modules.project.projectListener.event;

import com.solarize.solarizeWebBackend.modules.project.Project;

public record ProjectStatusChangedToRetryingEvent(Project project) {}
