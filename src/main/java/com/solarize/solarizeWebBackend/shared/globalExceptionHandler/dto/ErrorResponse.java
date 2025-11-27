package com.solarize.solarizeWebBackend.shared.globalExceptionHandler.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        String message,
        String status,
        String typeError,
        String path,
        String method,
        LocalDateTime timestamp
) {}

