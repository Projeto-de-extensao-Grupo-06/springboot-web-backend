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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects/{projectId}/comments")
public class ProjectCommentController {

    private final ProjectCommentService projectCommentService;

    @PreAuthorize("hasAuthority('PROJECT_WRITE')")
    @PostMapping
    public ResponseEntity<ProjectCommentResponseDTO> create(@PathVariable Long projectId, @Valid @RequestBody CreateProjectCommentRequestDTO requestDTO){
        ProjectCommentResponseDTO newProjectComment = ProjectCommentMapper.toDto(
                projectCommentService.create(
                        requestDTO.getAuthorId(),
                        projectId,
                        requestDTO.getComment()
                )
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newProjectComment);
    }

    @PreAuthorize("hasAuthority('PROJECT_READ')")
    @GetMapping
    public ResponseEntity<Page<ProjectCommentResponseDTO>> getCommentByProject(@PathVariable Long projectId, @PageableDefault (page = 0, size = 20, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable){
        Page<ProjectComment> comments = projectCommentService.listCommentsByProject(projectId,pageable);
        Page<ProjectCommentResponseDTO> dtoPage = comments.map(ProjectCommentMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    @PreAuthorize("hasAuthority('PROJECT_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id){
        projectCommentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
