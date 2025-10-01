package com.solarize.solarize_web_backend.shared.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solarize.solarize_web_backend.shared.exceptions.UnauthorizedException;
import com.solarize.solarize_web_backend.shared.globalExceptionHandler.dto.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class AuthEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException{

        if (authException.getClass().equals(BadCredentialsException.class) || authException.getClass().equals(InsufficientAuthenticationException.class)){
            ErrorResponse ex = new ErrorResponse(
                    "Invalid credentials",
                    String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                    request.getRequestURI(),
                    LocalDateTime.now()
            );

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            new ObjectMapper().writeValue(response.getOutputStream(), ex);
        }else{
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }

    }

}
