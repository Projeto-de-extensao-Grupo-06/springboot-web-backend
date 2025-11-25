package com.solarize.solarizeWebBackend.modules.project;

import lombok.RequiredArgsConstructor;
import com.solarize.solarizeWebBackend.modules.project.dto.ProjectSummaryDTO;
import com.solarize.solarizeWebBackend.modules.projectComment.ProjectCommentService;
import com.solarize.solarizeWebBackend.modules.projectFile.ProjectFileService;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ScheduleService scheduleService;
    private final ProjectCommentService projectCommentService;
    private final ProjectFileService projectFileService;
  
 
    public void setProjectStatusToRetrying(Project project) {
        project.getStatus().getStateHandler().applyToRetrying(project);
        projectRepository.save(project);
    }

    public Page<ProjectSummaryDTO> findAllProjectsSummary(String search,
                                                          List<ProjectStatusEnum> statusList,
                                                          Long responsibleId,
                                                          Long clientId,
                                                          Pageable pageable) {

        Page<Project> projects = projectRepository.findAllProjects(search, statusList, responsibleId, clientId, pageable);

        return projects.map(p -> {
            ProjectSummaryDTO dto = ProjectMapper.toSummary(p);
            dto.setNextSchedule(scheduleService.findNextScheduleDateByProjectId(p.getId()));
            dto.setCommentCount(projectCommentService.countCommentsByProjectId(p.getId()));
            dto.setFileCount(projectFileService.countFilesByProjectId(p.getId()));
            return dto;
        });
    }



}
