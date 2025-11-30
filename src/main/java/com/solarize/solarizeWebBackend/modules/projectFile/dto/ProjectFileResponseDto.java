package com.solarize.solarizeWebBackend.modules.projectFile.dto;

import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerResponseDto;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
    public class ProjectFileResponseDto {
        private Long id;
        private String originalFilename;
        private String contentType;
        private Integer mbSize;
        private Boolean homologationDoc;
        private String filename;
        private String checkSum;
        private LocalDateTime createdAt;
        private CoworkerResponseDto uploader;

    }
