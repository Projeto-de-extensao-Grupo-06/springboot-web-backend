package com.solarize.solarizeWebBackend.shared.globalExceptionHandler.dto;

import lombok.Builder;

@Builder
public record ValidationError(
        String field,
        String message
)
{}
