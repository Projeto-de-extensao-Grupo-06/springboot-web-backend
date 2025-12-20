package com.solarize.solarizeWebBackend.modules.projectFile;

import com.solarize.solarizeWebBackend.modules.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectFileRepository extends JpaRepository<ProjectFile, Long> {
    Integer countByProjectId(Long projectId);
    List<ProjectFile> findByCheckSum(String checkSum);
    List<ProjectFile> findAllByProject(Project project);
    List<ProjectFile> findAllByProjectId(Long projectId);

}
