package com.solarize.solarizeWebBackend.modules.coworker.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoworkerWithoutCredentialDto {
    private String firstName;
    private String lastName;
}
