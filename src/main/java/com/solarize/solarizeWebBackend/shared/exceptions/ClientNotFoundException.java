package com.solarize.solarizeWebBackend.shared.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ClientNotFoundException extends BaseException {
    public ClientNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
