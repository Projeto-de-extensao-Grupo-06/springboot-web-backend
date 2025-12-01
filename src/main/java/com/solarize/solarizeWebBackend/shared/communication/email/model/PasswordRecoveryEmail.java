package com.solarize.solarizeWebBackend.shared.communication.email.model;

import lombok.*;

@Getter
@Setter
@Builder
public class PasswordRecoveryEmail {
    private String to;
    private String subject;
    private String name;
    private String code;
    private String operatingSystem;
    private String browser;
    private String ip;
}
