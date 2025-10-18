package com.solarize.solarizeWebBackend.shared.globalExceptionHandler.dto;

import lombok.Builder;

@Builder
public record ValidationErrorDto (
        String field,
        String message
)
{}
