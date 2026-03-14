package com.solarize.solarizeWebBackend.modules.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientExistsResponseDto {
    private boolean exists;
    private Long clientId;
}
