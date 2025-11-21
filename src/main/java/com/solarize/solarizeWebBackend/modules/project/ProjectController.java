package com.solarize.solarizeWebBackend.modules.project;
import com.solarize.solarizeWebBackend.modules.project.dto.ProjectSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

import static io.micrometer.core.instrument.binder.grpc.GrpcObservationDocumentation.LowCardinalityKeyNames.SERVICE;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService service;
    @PreAuthorize("hasAuthority('PROJECT_READ')")
    @GetMapping()
    public ResponseEntity<Page<ProjectSummaryDTO>> getProjects(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) List<ProjectStatusEnum> status,
            @RequestParam(required = false) Long responsibleId,
            @RequestParam(required = false) Long clientId,
            @PageableDefault(page = 0, size = 20) Pageable pageable
    ) {
        Page<ProjectSummaryDTO> result =
                service.findAllProjectsSummary(search, status, responsibleId, clientId, pageable);

        return ResponseEntity.ok(result);
    }
}
