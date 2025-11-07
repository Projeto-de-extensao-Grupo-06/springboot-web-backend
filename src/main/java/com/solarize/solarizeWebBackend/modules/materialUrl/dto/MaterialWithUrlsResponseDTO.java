package com.solarize.solarizeWebBackend.modules.materialUrl.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialWithUrlsResponseDTO {
    private Long id;
    private String name;
    private List<MaterialUrlResponseDTO> links;
}
