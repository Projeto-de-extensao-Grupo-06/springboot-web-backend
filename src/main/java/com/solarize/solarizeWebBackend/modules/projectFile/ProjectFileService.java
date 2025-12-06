package com.solarize.solarizeWebBackend.modules.projectFile;

import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.coworker.CoworkerRepository;
import com.solarize.solarizeWebBackend.modules.project.ProjectRepository;
import com.solarize.solarizeWebBackend.shared.exceptions.BadRequestException;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import com.solarize.solarizeWebBackend.shared.files.FileStorageStrategy;
import com.solarize.solarizeWebBackend.shared.files.FileTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectFileService {

    private static final long maxBytes = 10L * 1024L * 1024L;
    private static final int maxFiles = 5;
    private final ProjectFileRepository repository;
    private final ProjectRepository projectRepository;
    private final CoworkerRepository coworkerRepository;
    private final FileStorageStrategy storageStrategy;


    public Integer countFilesByProjectId(Long projectId) {
        return repository.countByProjectId(projectId);
    }

    public List<ProjectFile> uploadFiles(Long projectId, List<MultipartFile> files, boolean isHomologation) {

        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));

        if (!Boolean.TRUE.equals(project.getIsActive())) {
            throw new BadRequestException("Project is not active");
        }

        if (files == null || files.isEmpty()) {
            throw new BadRequestException("No files were uploaded");
        }
        if (files.size() > maxFiles) {
            throw new BadRequestException("Maximum of " + maxFiles + " files per request");
        }

        List<ProjectFile> savedFiles = new ArrayList<>();

        final Path localUploadDir;
        try {
            localUploadDir = FileTransfer.defaultTmpUploadsDir();
        } catch (Exception e) {
            throw new BadRequestException("Failed to prepare upload directory: " + e.getMessage());
        }

        for (MultipartFile file : files) {
            if (file.getSize() > maxBytes) {
                throw new BadRequestException("File exceeds the maximum allowed size of " + (maxBytes / (1024 * 1024)) + " MB");
            }
            if (file.getOriginalFilename() == null || file.getOriginalFilename().isBlank()) {
                throw new BadRequestException("File name cannot be empty");
            }

            SavedFileInfo savedInfo;
            try {
                savedInfo = FileTransfer.saveAndHash(file, localUploadDir);
            } catch (Exception e) {
                throw new BadRequestException("Error while saving file locally: " + e.getMessage());
            }

            ProjectFile entity = new ProjectFile();
            entity.setProject(project);
            entity.setOriginalFilename(savedInfo.originalName());
            entity.setContentType(savedInfo.contentType());
            entity.setMbSize((int) Math.ceil(savedInfo.size() / 1024.0 / 1024.0));
            entity.setCheckSum(savedInfo.sha256());
            entity.setHomologationDoc(isHomologation);
            entity.setCreatedAt(LocalDateTime.now());
            entity.setUploader(getCurrentUser());

            var existingOpt = repository.findByCheckSum(savedInfo.sha256());
            if (!existingOpt.isEmpty()) {
                entity.setFilename(existingOpt.getFirst().getFilename());
            } else {

                String uuidName = UUID.randomUUID().toString();
                entity.setFilename(uuidName);

                final byte[] content;
                try {
                    content = Files.readAllBytes(savedInfo.path());
                } catch (Exception e) {
                    throw new BadRequestException("Error while reading file for storage: " + e.getMessage());
                }

                storageStrategy.save(uuidName, content);
            }

            ProjectFile saved = repository.save(entity);
            savedFiles.add(saved);

            try {
                    Files.deleteIfExists(savedInfo.path());
                } catch (Exception e) {
                    System.err.println("Failed to delete temporary file: " + savedInfo.path());
                }
            }

        return savedFiles;
    }

    private Coworker getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof com.solarize.solarizeWebBackend.modules.auth.dtos.CoworkerDetailsDto details)) {
            throw new BadRequestException("User is not authenticated");

        }
        return coworkerRepository.findById(details.getId())
                .orElseThrow(() -> new NotFoundException("Authenticated user not found"));
    }

    public List<ProjectFile> listFiles(Long projectId) {

        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));

        List<ProjectFile> files = repository.findAllByProject(project);

        return files;
    }

    public ProjectFile downloadFile(Long fileId) {
        ProjectFile file = repository.findById(fileId)
                .orElseThrow(() -> new NotFoundException("File not found"));

        byte[] bytes;
        try {
            bytes = storageStrategy.load(file.getFilename());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load file from storage", e);
        }

        if (bytes == null || bytes.length == 0) {
            throw new NotFoundException("Stored file not found");
        }


        return file;
    }

    public byte[] getFileContent(ProjectFile file) {
        try {
            byte[] bytes = storageStrategy.load(file.getFilename());
            if (bytes == null || bytes.length == 0) {
                throw new NotFoundException("Stored file not found");
            }
            return bytes;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load file from storage", e);
        }
    }


}

