package com.solarize.solarizeWebBackend.modules.projectComment;

import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.coworker.CoworkerRepository;
import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectRepository;
import com.solarize.solarizeWebBackend.modules.projectComment.DTO.CreateProjectCommentRequestDTO;
import com.solarize.solarizeWebBackend.modules.projectComment.DTO.ProjectCommentMapper;
import com.solarize.solarizeWebBackend.modules.projectComment.DTO.ProjectCommentResponseDTO;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProjectCommentService {

    private final ProjectCommentRepository repository;
    private final ProjectRepository projectRepository;
    private final CoworkerRepository coworkerRepository;


    public Integer countCommentsByProjectId(Long projectId) {
        return repository.countByProjectId(projectId);
    }

    public ProjectComment create(Long projectId, CreateProjectCommentRequestDTO dto) {

        Coworker author = coworkerRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Author not found with id: ".formatted(dto.getAuthorId())));

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new NotFoundException("Project not found with id: ".formatted(dto.getProjectId())));

        ProjectComment comment = new ProjectComment();
        comment.setAuthor(author);
        comment.setProject(project);
        comment.setComment(dto.getComment());
        comment.setCreatedAt(LocalDateTime.now());

        return repository.save(comment);

    }

    public Page<ProjectCommentResponseDTO> listCommentsByProject(Long projectId, Pageable pageable){

        if (!projectRepository.existsById(projectId)){
            throw new NotFoundException("Project not found");
        }

        return repository.findAllByProject(projectId,pageable).map(ProjectCommentMapper::toDto);

    }

    public void deleteComment(Long id) throws AccessDeniedException {

        ProjectComment comment = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comments not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String currentUserEmail = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !comment.getAuthor().getEmail().equals(currentUserEmail)){
            throw new AccessDeniedException("You are not allowed  to delete others users comments");
        }

        repository.delete(comment);

    }

}
