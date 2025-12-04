package com.solarize.solarizeWebBackend.modules.projectComment;


import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.coworker.CoworkerRepository;
import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectRepository;
import com.solarize.solarizeWebBackend.modules.projectComment.dto.CreateProjectCommentRequestDTO;
import com.solarize.solarizeWebBackend.modules.projectComment.dto.ProjectCommentMapper;
import com.solarize.solarizeWebBackend.modules.projectComment.dto.ProjectCommentResponseDTO;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projectId/comments")
public class ProjectCommentController {

    private final ProjectCommentService projectCommentService;
    private final ProjectRepository projectRepository;
    private final CoworkerRepository coworkerRepository;


    @PreAuthorize("hasAuthority('PROJECT_UPDATE')")
    @PostMapping
    public ResponseEntity<ProjectCommentResponseDTO> create(@PathVariable Long projectId, @Valid @RequestBody CreateProjectCommentRequestDTO requestDTO){

        Coworker author = coworkerRepository.findById(requestDTO.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Author not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));

        ProjectComment projectComment = ProjectCommentMapper.toEntity(requestDTO, author,project);

        ProjectComment newProjectComment = projectCommentService.create(projectComment);

        return ResponseEntity.status(201).body(ProjectCommentMapper.toDto(newProjectComment));

    }

    @PreAuthorize("hasAuthority('PROJECT_READ')")
    @GetMapping
    public ResponseEntity<Page<ProjectCommentResponseDTO>> getCommentByProject(@PathVariable Long projectId, @PageableDefault (page = 0, size = 20, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable){

        Page<ProjectComment> comments = projectCommentService.listCommentsByProject(projectId,pageable);
        Page<ProjectCommentResponseDTO> dtoPage = comments.map(ProjectCommentMapper::toDto);

        return ResponseEntity.ok(dtoPage);
        

    }

    @PreAuthorize("hasAuthority('PROJECT_UPDATE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id){

        projectCommentService.deleteComment(id);

        return ResponseEntity.noContent().build();

    }


}
