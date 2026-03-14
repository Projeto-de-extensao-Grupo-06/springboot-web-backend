package com.solarize.solarizeWebBackend.modules.projectComment.dto;

import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerResponseDto;
import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerWithoutCredentialDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCommentResponseDTO {

    private Long id;
    private CoworkerWithoutCredentialDto author;
    private String comment;
    private LocalDateTime createdAt;

}
