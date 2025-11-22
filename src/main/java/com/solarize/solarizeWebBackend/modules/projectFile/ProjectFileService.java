package com.solarize.solarizeWebBackend.modules.projectFile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectFileService {
    private final ProjectFileRepository repository;

    public Integer countFilesByProjectId(Long projectId) {
        return repository.countByProjectId(projectId);
    }
}
