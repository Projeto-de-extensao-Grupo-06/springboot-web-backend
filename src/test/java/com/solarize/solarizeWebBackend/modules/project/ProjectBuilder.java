package com.solarize.solarizeWebBackend.modules.project;

import com.solarize.solarizeWebBackend.modules.schedule.Schedule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProjectBuilder {
    private final LocalDateTime createdAt = LocalDateTime.now();
    private ProjectStatusEnum status = ProjectStatusEnum.NEW;
    private ProjectStatusEnum previewStatus;
    private final List<Schedule> schedules = new ArrayList<>();

    public static ProjectBuilder builder () {
        return new ProjectBuilder();
    }

    public ProjectBuilder withPreviewStatus(ProjectStatusEnum status) {
        this.previewStatus = status;
        return this;
    }

    public ProjectBuilder withStatus(ProjectStatusEnum status) {
        this.status = status;
        return this;
    }

    public ProjectBuilder withSchedules(List<Schedule> schedules) {
        this.schedules.addAll(schedules);
        return this;
    }

    public Project build() {
        Project project = new Project();

        Long id = 1L;
        String name = "Test Project";
        Boolean isActive = true;

        project.setId(id);
        project.setName(name);
        project.setIsActive(isActive);
        project.setStatus(this.status);
        project.setPreviewStatus(this.previewStatus);
        project.setCreatedAt(this.createdAt);
        project.setSchedules(this.schedules);

        return project;
    }
}
