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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponseDTO>createProject(@RequestBody @Valid CreateProjectRequestDTO requestDTO){

        Project project = ProjectMapper.toEntity(requestDTO);

        Project newProject = projectService.createManualProject( project, requestDTO.getClientId(),requestDTO.getAddressId());

        return ResponseEntity.status(201).body(ProjectMapper.toDto(newProject));
    }



}
