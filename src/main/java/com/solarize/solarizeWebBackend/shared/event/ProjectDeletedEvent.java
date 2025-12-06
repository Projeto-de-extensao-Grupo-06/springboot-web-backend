package com.solarize.solarizeWebBackend.shared.event;

import com.solarize.solarizeWebBackend.modules.project.Project;

public record ProjectDeletedEvent(
        Project project
) {
}
