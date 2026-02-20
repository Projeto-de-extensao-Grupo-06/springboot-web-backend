package com.solarize.solarizeWebBackend.modules.coworker.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoworkerResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

}
