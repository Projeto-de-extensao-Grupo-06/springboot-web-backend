package com.solarize.solarize_web_backend.shared.globalExceptionHandler;

import com.solarize.solarize_web_backend.shared.exceptions.InternalServerError;
import com.solarize.solarize_web_backend.shared.exceptions.UnauthorizedException;
import com.solarize.solarize_web_backend.shared.globalExceptionHandler.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request, Exception exception) {
//        InternalServerError ex = new InternalServerError();
//
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
//                new ErrorResponse(
//                        ex.getMessage(),
//                        ex.getSTATUS(),
//                        ex.getSTATUS_DESC(),
//                        ex.getPATH(),
//                        ex.getTIMESTAMP()
//                )
//        );
//    }
}
