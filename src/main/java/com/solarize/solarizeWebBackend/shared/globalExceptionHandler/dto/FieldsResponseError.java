package com.solarize.solarizeWebBackend.shared.globalExceptionHandler.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record FieldsResponseError(
        String message,
        String status,
        String typeError,
        String path,
        String method,
        LocalDateTime timestamp,
        List<ValidationError> validationErrors
) {
}
