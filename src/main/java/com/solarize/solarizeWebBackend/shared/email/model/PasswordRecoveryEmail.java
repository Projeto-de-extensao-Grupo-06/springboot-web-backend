package com.solarize.solarizeWebBackend.shared.email.model;

import lombok.*;

@Getter
@Setter
public class PasswordRecoveryEmail extends BaseEmail {
    private String name;
    private String code;
    private String operatingSystem;
    private String browser;

    public PasswordRecoveryEmail(String to, String subject, String template, String name, String code, String operatingSystem, String browser) {
        super(to, subject, template);
        this.name = name;
        this.code = code;
        this.operatingSystem = operatingSystem;
        this.browser = browser;
    }
}
