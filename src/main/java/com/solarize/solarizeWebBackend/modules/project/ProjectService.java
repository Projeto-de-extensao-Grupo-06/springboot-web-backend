package com.solarize.solarizeWebBackend.modules.project;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository repository;

    public void setProjectStatusToRetrying(Project project) {
        project.getStatus().getStateHandler().applyToRetrying(project);
        repository.save(project);
    }
}
