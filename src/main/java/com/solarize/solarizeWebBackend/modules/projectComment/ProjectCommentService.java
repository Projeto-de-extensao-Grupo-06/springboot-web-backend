package com.solarize.solarizeWebBackend.modules.projectComment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectCommentService {
    private final ProjectCommentRepository repository;

    public Integer countCommentsByProjectId(Long projectId) {
        return repository.countByProjectId(projectId);
    }
}
