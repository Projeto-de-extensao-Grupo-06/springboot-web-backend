package com.solarize.solarizeWebBackend.shared.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Getter
public class BaseException extends RuntimeException {
    private final LocalDateTime TIMESTAMP;
    private final String STATUS;
    private final String STATUS_DESC;

    public BaseException(String message, HttpStatus status) {
        super(message);

        this.STATUS = String.valueOf(status.value());
        this.STATUS_DESC = status.getReasonPhrase();
        this.TIMESTAMP = LocalDateTime.now();
    }
}
