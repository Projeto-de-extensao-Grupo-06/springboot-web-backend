package com.solarize.solarizeWebBackend.shared.globalExceptionHandler;

import com.solarize.solarizeWebBackend.shared.exceptions.BaseException;
import com.solarize.solarizeWebBackend.shared.globalExceptionHandler.dto.ErrorResponse;
import com.solarize.solarizeWebBackend.shared.globalExceptionHandler.dto.FieldsResponseError;
import com.solarize.solarizeWebBackend.shared.globalExceptionHandler.dto.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.List;

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

    public static FieldsResponseError of(MethodArgumentNotValidException ex) {
        List<ValidationError> validationsErros =  ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> new ValidationError(e.getField(), e.getDefaultMessage()))
                .toList();

        return FieldsResponseError
                .builder()
                .message("Fields Validation error")
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .typeError(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .path(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI())
                .timestamp(LocalDateTime.now())
                .validationErrors(validationsErros)
                .build();
    }

    public static ErrorResponse internalServerError() {
        return new ErrorResponse(
                "Internal Server Error",
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI(),
                LocalDateTime.now()
        );
    }

    public static ErrorResponse of(HttpMessageNotReadableException ex) {
        return ErrorResponse
                .builder()
                .message("The request body is malformed or contains invalid data. Please check your JSON payload and try again.")
                .path(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI())
                .typeError(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ErrorResponse uriNotFound() {
        return ErrorResponse
                .builder()
                .message("Endpoint not found.")
                .path(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI())
                .typeError(HttpStatus.NOT_FOUND.getReasonPhrase())
                .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .timestamp(LocalDateTime.now())
                .build();
    }


    public static ErrorResponse of(BadCredentialsException ex) {
        return ErrorResponse
                .builder()
                .message(ex.getMessage())
                .path(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI())
                .typeError(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .status(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ErrorResponse of(HttpRequestMethodNotSupportedException ex) {
        return ErrorResponse
                .builder()
                .message("Request method is not supported")
                .path(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI())
                .typeError(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
                .status(String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()))
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ErrorResponse of(AuthorizationDeniedException ex) {
        return ErrorResponse
                .builder()
                .message("You do not have permission to request this endpoint.")
                .path(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI())
                .typeError(HttpStatus.FORBIDDEN.getReasonPhrase())
                .status(String.valueOf(HttpStatus.FORBIDDEN.value()))
                .timestamp(LocalDateTime.now())
                .build();
    }
}
