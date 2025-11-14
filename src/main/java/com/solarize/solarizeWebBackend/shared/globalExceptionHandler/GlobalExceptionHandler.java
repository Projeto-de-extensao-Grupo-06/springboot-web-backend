package com.solarize.solarizeWebBackend.shared.globalExceptionHandler;

import com.solarize.solarizeWebBackend.shared.exceptions.*;
import com.solarize.solarizeWebBackend.shared.globalExceptionHandler.dto.ErrorResponse;
import com.solarize.solarizeWebBackend.shared.globalExceptionHandler.dto.FieldsResponseError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        return new ResponseEntity<>(ExceptionsMapper.of(ex), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(AuthorizationDeniedException ex) {
        return new ResponseEntity<>(ExceptionsMapper.of(ex), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ExceptionsMapper.of(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(ExceptionsMapper.of(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FieldsResponseError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ExceptionsMapper.of(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(ExceptionsMapper.of(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException ex) {
        return new ResponseEntity<>(ExceptionsMapper.of(ex), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TooManyRequestsException.class)
    public ResponseEntity<ErrorResponse> handleToManyRequestsException(TooManyRequestsException ex) {
        return new ResponseEntity<>(ExceptionsMapper.of(ex), HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException() {
        return new ResponseEntity<>(ExceptionsMapper.uriNotFound(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleServerErrorException(ServerErrorException ex) {
        log.error("Handled exception", ex);
        return new ResponseEntity<>(ExceptionsMapper.of(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>(ExceptionsMapper.of(ex), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(InvalidDocumentException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDocumentException(InvalidDocumentException ex) {
        return new ResponseEntity<>(ExceptionsMapper.of(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>(ExceptionsMapper.of(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        log.error("Unhandled exception", ex);
        return new ResponseEntity<>(ExceptionsMapper.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
