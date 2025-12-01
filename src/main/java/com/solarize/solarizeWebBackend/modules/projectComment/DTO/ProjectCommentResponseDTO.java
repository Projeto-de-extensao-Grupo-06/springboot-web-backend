package com.solarize.solarizeWebBackend.modules.projectComment.DTO;

import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerResponseDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCommentResponseDTO {

    private Long id;
    private CoworkerResponseDto author;
    private String comment;
    private LocalDateTime createdAt;

}
