package com.solarize.solarizeWebBackend.modules.project;

import com.solarize.solarizeWebBackend.modules.project.dto.ProjectSummaryDTO;
import com.solarize.solarizeWebBackend.modules.schedule.Schedule;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleTypeEnum;
import com.solarize.solarizeWebBackend.shared.event.ScheduleCreatedEvent;
import com.solarize.solarizeWebBackend.modules.projectComment.ProjectCommentService;
import com.solarize.solarizeWebBackend.modules.projectFile.ProjectFileService;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleService;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidStateTransitionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ScheduleService scheduleService;
    private final ProjectCommentService projectCommentService;
    private final ProjectFileService projectFileService;

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

    @EventListener
    public void projectUpdateOnScheduleCreate(ScheduleCreatedEvent event) {
        if(event.projectId() == null) {
            return;
        }

        if(event.type() == ScheduleTypeEnum.NOTE) {
            return;
        }

        try {
            Project project = projectRepository.findById(event.projectId()).orElseThrow();

            if(event.type() == ScheduleTypeEnum.TECHNICAL_VISIT) {
                project.getStatus().getState().applyToScheduledTechnicalVisit(project);
            }

            else if(event.type() == ScheduleTypeEnum.INSTALL_VISIT) {
                project.getStatus().getState().applyToScheduledInstallingVisit(project);
            }

            projectRepository.save(project);
        } catch (InvalidStateTransitionException ex) {
            log.warn("Invalid project state transition");
        }
    }
}
