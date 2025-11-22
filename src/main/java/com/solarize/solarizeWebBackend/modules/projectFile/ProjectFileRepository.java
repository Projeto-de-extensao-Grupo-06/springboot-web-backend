package com.solarize.solarizeWebBackend.modules.projectFile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectFileRepository extends JpaRepository<ProjectFile, Long> {
    Integer countByProjectId(Long projectId);
}
