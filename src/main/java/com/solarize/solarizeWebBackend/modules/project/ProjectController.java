package com.solarize.solarizeWebBackend.modules.project;

import com.solarize.solarizeWebBackend.modules.project.dto.CreateProjectRequestDTO;
import com.solarize.solarizeWebBackend.modules.project.dto.ProjectMapper;
import com.solarize.solarizeWebBackend.modules.project.dto.ProjectResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import com.solarize.solarizeWebBackend.modules.project.dto.ProjectSummaryDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    
    @PreAuthorize("hasAuthority('PROJECT_WRITE')")
    @PostMapping
    public ResponseEntity<ProjectResponseDTO>createProject(@RequestBody @Valid CreateProjectRequestDTO requestDTO){

        Project project = ProjectMapper.toEntity(requestDTO);

        Project newProject = projectService.createManualProject( project, requestDTO.getClientId(),requestDTO.getAddressId());

        return ResponseEntity.status(201).body(ProjectMapper.toDto(newProject));
    }
  
    @PreAuthorize("hasAuthority('PROJECT_READ')")
    @GetMapping
    public ResponseEntity<Page<ProjectSummaryDTO>> getProjects(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) List<ProjectStatusEnum> status,
            @RequestParam(required = false) Long responsibleId,
            @RequestParam(required = false) Long clientId,
            @PageableDefault(page = 0, size = 20) Pageable pageable
    ) {
        Page<ProjectSummaryDTO> result =
                projectService.findAllProjectsSummary(search, status, responsibleId, clientId, pageable);

        return ResponseEntity.ok(result);
    }
  }