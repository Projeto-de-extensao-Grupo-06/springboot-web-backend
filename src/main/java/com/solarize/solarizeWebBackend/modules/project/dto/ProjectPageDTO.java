package com.solarize.solarizeWebBackend.modules.project.dto;

import lombok.*;

import java.util.List;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPageDTO {

    private Long totalRecords;
    private Integer totalPages;
    private Integer totalResults;
    private List<ProjectSummaryDTO> projects;

}
