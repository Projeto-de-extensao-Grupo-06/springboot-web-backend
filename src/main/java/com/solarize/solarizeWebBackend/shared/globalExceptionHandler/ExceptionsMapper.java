package com.solarize.solarizeWebBackend.shared.globalExceptionHandler;

import com.solarize.solarizeWebBackend.shared.exceptions.BaseException;
import com.solarize.solarizeWebBackend.shared.globalExceptionHandler.dto.ErrorResponse;

public class ExceptionsMapper {
    public static ErrorResponse of(BaseException ex) {
        return ErrorResponse
                .builder()
                .message(ex.getMessage())
                .status(ex.getSTATUS())
                .typeError(ex.getSTATUS_DESC())
                .path(ex.getPATH())
                .timestamp(ex.getTIMESTAMP())
                .build();
    }
}
