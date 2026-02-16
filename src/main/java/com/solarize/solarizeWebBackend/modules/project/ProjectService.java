package com.solarize.solarizeWebBackend.modules.project;

import com.solarize.solarizeWebBackend.modules.address.Address;
import com.solarize.solarizeWebBackend.modules.address.AddressRepository;
import com.solarize.solarizeWebBackend.modules.client.Client;
import com.solarize.solarizeWebBackend.modules.client.ClientRepository;
import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.coworker.CoworkerRepository;
import com.solarize.solarizeWebBackend.modules.project.dto.response.ProjectSummaryDTO;
import com.solarize.solarizeWebBackend.modules.schedule.Schedule;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleStatusEnum;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleTypeEnum;
import com.solarize.solarizeWebBackend.shared.event.BudgetCreateEvent;
import com.solarize.solarizeWebBackend.shared.event.ProjectDeletedEvent;
import com.solarize.solarizeWebBackend.shared.event.ScheduleCreatedEvent;
import com.solarize.solarizeWebBackend.modules.projectComment.ProjectCommentService;
import com.solarize.solarizeWebBackend.modules.projectFile.ProjectFileService;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleService;
import com.solarize.solarizeWebBackend.shared.exceptions.ConflictException;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidStateTransitionException;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
  
    private final ScheduleService scheduleService;
    private final ProjectCommentService projectCommentService;
    private final ProjectFileService projectFileService;
    private final CoworkerRepository coworkerRepository;
    private final ApplicationEventPublisher eventPublisher;

    public Project projectManualCreate(Project project) {
        if(project.getAddress().getId() != null && !addressRepository.existsById(project.getAddress().getId())) {
            throw new NotFoundException("Address does not exists on database");
        }

        if(!clientRepository.existsById(project.getClient().getId())) {
            throw new NotFoundException("Client does not exists on database");
        }

        Coworker responsibleCoworker = coworkerRepository
                .findById(project.getResponsible().getId())
                .orElseThrow(() ->
                        new NotFoundException("Responsible coworker not found"));

        if (project.getStatus() == null) {
            project.setStatus(ProjectStatusEnum.NEW);
        }

        project.setIsActive(true);
        project.setCreatedAt(LocalDateTime.now());
        project.setResponsible(responsibleCoworker);
        project.setProjectFrom(ProjectSourceEnum.INTERNAL_MANUAL_ENTRY);

        return projectRepository.save(project);
    }

    @Transactional
    public Project projectUpdate(Project incoming, Long projectId) {
        Project existing = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found."));


        if(incoming.getClient() != null && incoming.getClient().getId() != null) {
            Client found = clientRepository.findById(incoming.getClient().getId())
                    .orElseThrow(() -> new NotFoundException("Client not found."));

            existing.setClient(found);
        }

        existing.setName(
                Optional.ofNullable(incoming.getName())
                        .orElse(existing.getName())
        );

        existing.setDescription(
                Optional.ofNullable(incoming.getDescription())
                        .orElse(existing.getDescription())
        );

        existing.setSystemType(
                Optional.ofNullable(incoming.getSystemType())
                        .orElse(existing.getSystemType())
        );

        return projectRepository.save(existing);
    }



    public Project createManualProject(Project project, @NotNull(message = "Client ID is required") Long clientId, Long addressId){

        if (projectRepository.existsByName(project.getName())){
            throw new ConflictException("Project name already exists");
        }

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found with id: %d".formatted(clientId)));

        Optional<Address> address = addressRepository.findById(addressId);
        if (address.isEmpty()) {
            throw new NotFoundException("Address not found with id: %d".formatted(addressId));
        }

        project.setName(project.getName());
        project.setClient(client);
        project.setAddress(address.get());
        project.setProjectFrom(ProjectSourceEnum.INTERNAL_MANUAL_ENTRY);
        project.setStatus(ProjectStatusEnum.NEW);
        project.setCreatedAt(LocalDateTime.now());
        project.setIsActive(true);

        return projectRepository.save(project);
    }

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

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project does not exists."));
    }

    public void softDeleteProject(Long projectId) {
        Project project = projectRepository.findByIdAndIsActiveTrue(projectId)
                .orElseThrow(() -> new NotFoundException("Project does not exists"));

        project.setIsActive(false);
        projectRepository.save(project);

        eventPublisher.publishEvent(new ProjectDeletedEvent(project));
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
                project.getStatus().getValue().applyToScheduledTechnicalVisit(project);
            }

            else if(event.type() == ScheduleTypeEnum.INSTALL_VISIT) {
                project.getStatus().getValue().applyToScheduledInstallingVisit(project);
            }

            projectRepository.save(project);
        } catch (InvalidStateTransitionException ex) {
            log.warn("Invalid project state transition");
        }
    }

    @EventListener
    public void projectBudgetCreated(BudgetCreateEvent event) {
        Project project = projectRepository.findById(event.projectId()).orElseThrow();

        System.out.println(event.finalBudget());

        if(event.finalBudget()) {
            project.getStatus().getStateHandler().applyToFinalBudget(project);
        } else {
            project.getStatus().getStateHandler().applyToPreBudget(project);
        }

        projectRepository.save(project);
    }

    public List<Project> getProjectsByClientId(Long clientId){
        if(!clientRepository.existsById(clientId))
            throw new NotFoundException("Client does not exists on database");

        return projectRepository.findByClientIdAndIsActiveTrue(clientId);
    }

    public List<Project> getLeads(LocalDateTime minDate, LocalDateTime maxDate, ProjectStatusEnum status, String clientName){
        return projectRepository.findActionableLeads(minDate, maxDate, status, clientName);
    }

    public void changeStatusClientAwaitingContact(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project does not exists"));
        project.getStatus().getStateHandler().applyToClientAwaitingContact(project);
        projectRepository.save(project);
    }

    public  List<Schedule> getSchedulesByProjectId(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project does not exists"));

        return project.getSchedules().stream().filter(s -> s.getStatus() == ScheduleStatusEnum.MARKED).toList();
    }
}
