package com.solarize.solarizeWebBackend.modules.projectComment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectCommentRepository extends JpaRepository<ProjectComment, Long> {
    Integer countByProjectId(@Param("projectId") Long projectId);
}
