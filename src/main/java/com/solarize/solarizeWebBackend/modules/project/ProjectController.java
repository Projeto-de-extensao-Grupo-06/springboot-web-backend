package com.solarize.solarizeWebBackend.modules.project;
import com.solarize.solarizeWebBackend.modules.project.dto.request.ProjectManualCreateDto;
import com.solarize.solarizeWebBackend.modules.project.dto.request.ProjectUpdateDto;
import com.solarize.solarizeWebBackend.modules.project.dto.response.ProjectDto;
import com.solarize.solarizeWebBackend.modules.project.dto.response.ProjectSummaryDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    @PreAuthorize("hasAuthority('PROJECT_WRITE')")
    @PostMapping("/manual")
    public ResponseEntity<ProjectDto> manualProjectCreate(@RequestBody @Valid ProjectManualCreateDto dto) {
        Project createdProject = projectService.projectManualCreate(ProjectMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(ProjectMapper.toDto(createdProject));
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

    @PreAuthorize("hasAuthority('PROJECT_READ')")
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long projectId) {
        Project project = projectService.getProjectById(projectId);
        return ResponseEntity.ok(ProjectMapper.toDto(project));
    }

    @PreAuthorize("hasAuthority('PROJECT_UPDATE')")
    @PatchMapping("/{projectId}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable Long projectId, @RequestBody @Valid ProjectUpdateDto dto) {
        Project project = projectService.projectUpdate(ProjectMapper.toEntity(dto), projectId);
        return ResponseEntity.ok(ProjectMapper.toDto(project));
    }


    @PreAuthorize("hasAuthority('PROJECT_DELETE')")
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProjectById(@PathVariable Long projectId) {
        projectService.softDeleteProject(projectId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasAuthority('PROJECT_READ')")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ProjectSummaryDTO>> getProjectsByClientId(@PathVariable Long clientId) {
        List<Project> projects = projectService.getProjectsByClientId(clientId);
        if(projects.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(ProjectMapper.toSummary(projects));
    }
}
