package com.solarize.solarizeWebBackend.modules.projectFile.dtos;

import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ProjectFileGetResponseDTO {
    private Long id;
    private String originalFilename;
    private Integer mbSize;
    private Boolean homologationDoc;
    private LocalDateTime createdAt;


    private CoworkerResponseDto uploader;
}
