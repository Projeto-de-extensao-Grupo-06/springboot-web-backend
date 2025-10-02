package com.solarize.solarize_web_backend.shared.globalExceptionHandler.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        String status,
        String typeError,
        String path,
        LocalDateTime timestamp
) {}

