package com.solarize.solarizeWebBackend.modules.project.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import com.solarize.solarizeWebBackend.modules.address.dto.CreateAddressDto;
import jakarta.validation.Valid;

@Data
public class ProjectBotLeadCreateDto {
    private String firstName;
    private String lastName;
    
    @NotBlank(message = "O telefone é obrigatório")
    private String phone;
    
    @NotBlank(message = "O valor da conta é obrigatório")
    private String monthlyBill;
    
    private String cityState;
    private String propertyType;
    
    @Valid
    private CreateAddressDto address;
}
