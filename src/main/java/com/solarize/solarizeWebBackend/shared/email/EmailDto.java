package com.solarize.solarizeWebBackend.shared.email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDto {
    private String to;
    private String subject;
    private String body;



}
