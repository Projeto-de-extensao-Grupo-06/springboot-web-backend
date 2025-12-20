package com.solarize.solarizeWebBackend.modules.projectComment.dto;

import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.coworker.CoworkerMapper;
import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.projectComment.ProjectComment;

import java.time.LocalDateTime;


public class ProjectCommentMapper {

    public static ProjectCommentResponseDTO toDto (ProjectComment project){

        if (project == null) return null;

        return ProjectCommentResponseDTO.builder()
                .id(project.getId())
                .author(CoworkerMapper.toDto(project.getAuthor()))
                .comment(project.getComment())
                .createdAt(project.getCreatedAt())
                .build();

    }

    public static ProjectComment toEntity(CreateProjectCommentRequestDTO dto, Coworker author, Project project){

        if (dto == null) return null;

        ProjectComment comment = new ProjectComment();
        comment.setAuthor(author);
        comment.setProject(project);
        comment.setComment(dto.getComment());
        comment.setCreatedAt(LocalDateTime.now());

        return comment;

    }

}
