package com.solarize.solarizeWebBackend.modules.project;

import com.solarize.solarizeWebBackend.modules.address.Address;
import com.solarize.solarizeWebBackend.modules.address.AddressRepository;
import com.solarize.solarizeWebBackend.modules.client.Client;
import com.solarize.solarizeWebBackend.modules.client.ClientRepository;
import com.solarize.solarizeWebBackend.modules.project.dto.ProjectSummaryDTO;
import com.solarize.solarizeWebBackend.modules.projectComment.ProjectCommentService;
import com.solarize.solarizeWebBackend.modules.projectFile.ProjectFileService;
import com.solarize.solarizeWebBackend.modules.schedule.ScheduleService;
import com.solarize.solarizeWebBackend.shared.exceptions.ConflictException;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
  
    private final ScheduleService scheduleService;
    private final ProjectCommentService projectCommentService;
    private final ProjectFileService projectFileService;

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

}
