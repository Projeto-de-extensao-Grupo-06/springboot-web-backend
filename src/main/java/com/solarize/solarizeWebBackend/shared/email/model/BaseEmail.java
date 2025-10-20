package com.solarize.solarizeWebBackend.shared.email.model;

import lombok.*;

@Getter
@Setter
abstract public class BaseEmail {
    private String to;
    private String subject;
    private String template;

    public BaseEmail(String to, String subject, String template) {
        this.to = to;
        this.subject = subject;
        this.template = template;
    }
}
