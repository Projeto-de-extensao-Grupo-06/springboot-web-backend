package com.solarize.solarizeWebBackend.modules.projectFile;

import com.solarize.solarizeWebBackend.modules.coworker.CoworkerMapper;
import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerResponseDto;
import com.solarize.solarizeWebBackend.modules.projectFile.dtos.ProjectFileGetResponseDto;
import com.solarize.solarizeWebBackend.modules.projectFile.dtos.ProjectFileResponseDto;

public class ProjectFileMapper {

    public static ProjectFileResponseDto toDto(ProjectFile entity) {
        return ProjectFileResponseDto.builder()
                .id(entity.getId())
                .originalFilename(entity.getOriginalFilename())
                .contentType(entity.getContentType())
                .mbSize(entity.getMbSize())
                .homologationDoc(entity.getHomologationDoc())
                .filename(entity.getFilename())
                .checkSum(entity.getCheckSum())
                .createdAt(entity.getCreatedAt())
                .uploader(CoworkerResponseDto.builder()
                        .id(entity.getUploader().getId())
                        .firstName(entity.getUploader().getFirstName())
                        .lastName(entity.getUploader().getLastName())
                        .email(entity.getUploader().getEmail())
                        .phone(entity.getUploader().getPhone())
                        .build())
                .build();
    }

    public static ProjectFileGetResponseDto toGetDto(ProjectFile file) {
        return ProjectFileGetResponseDto.builder()
                .id(file.getId())
                .originalFilename(file.getOriginalFilename())
                .mbSize(file.getMbSize())
                .homologationDoc(file.getHomologationDoc())
                .createdAt(file.getCreatedAt())
                .uploader(CoworkerMapper.toDto(file.getUploader()))
                .build();
    }


}
