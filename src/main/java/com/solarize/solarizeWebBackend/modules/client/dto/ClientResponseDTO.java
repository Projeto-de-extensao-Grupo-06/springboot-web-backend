package com.solarize.solarizeWebBackend.modules.client.dto;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponseDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private String documentType;
    private String cnpj;
    private String note;
    private String phone;
    private String email;
}
