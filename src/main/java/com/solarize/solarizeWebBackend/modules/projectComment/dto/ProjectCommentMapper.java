package com.solarize.solarizeWebBackend.modules.projectComment.dto;

import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.coworker.CoworkerMapper;
import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.projectComment.ProjectComment;

import java.time.LocalDateTime;


public class ProjectCommentMapper {

    public static ProjectCommentResponseDTO toDto (ProjectComment entity){

        if (entity == null) return null;

        return ProjectCommentResponseDTO.builder()
                .id(entity.getId())
                .author(CoworkerMapper.toDto(entity.getAuthor()))
                .comment(entity.getComment())
                .createdAt(entity.getCreatedAt())
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
