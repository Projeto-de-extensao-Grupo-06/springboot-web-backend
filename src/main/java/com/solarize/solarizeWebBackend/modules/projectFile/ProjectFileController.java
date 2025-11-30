package com.solarize.solarizeWebBackend.modules.projectFile;

import com.solarize.solarizeWebBackend.modules.projectFile.dto.ProjectFileGetResponseDTO;
import com.solarize.solarizeWebBackend.modules.projectFile.dto.ProjectFileMapper;
import com.solarize.solarizeWebBackend.modules.projectFile.dto.ProjectFileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects/{projectId}/files")
@RequiredArgsConstructor
public class ProjectFileController {
    private final ProjectFileService service;

    @PreAuthorize("hasAuthority('PROJECT_UPDATE')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ProjectFileResponseDto>> uploadProjectFiles(
            @PathVariable Long projectId,
            @RequestParam List<MultipartFile> files,
            @RequestParam boolean isHomologation
    ) {

        List<ProjectFile> saved = service.uploadFiles(projectId, files, isHomologation);

        List<ProjectFileResponseDto> response = saved.stream()
                .map(ProjectFileMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(201).body(response);

    }

    @PreAuthorize("hasAuthority('PROJECT_READ')")
    @GetMapping
    public ResponseEntity<List<ProjectFileGetResponseDTO>> listProjectFiles(@PathVariable Long projectId) {

        List<ProjectFile> files = service.listFiles(projectId);

        if (files.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        List<ProjectFileGetResponseDTO> response = files.stream()
                .map(ProjectFileMapper::toGetDto)
                .toList();

        return ResponseEntity.status(200).body(response);
    }

    @PreAuthorize("hasAuthority('PROJECT_READ')")
    @GetMapping("/{fileId}/download")
    public ResponseEntity<ByteArrayResource> download(@PathVariable Long fileId) {


        ProjectFile file = service.downloadFile(fileId);


        byte[] content = service.getFileContent(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginalFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, file.getContentType() != null ? file.getContentType() : "application/octet-stream")
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(content.length))
                .body(new ByteArrayResource(content));
    }}

