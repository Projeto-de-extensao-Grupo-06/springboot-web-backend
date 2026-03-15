package com.solarize.solarizeWebBackend.modules.project.dto.request;

import com.solarize.solarizeWebBackend.modules.address.dto.CreateAddressDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectSiteLeadCreateDto {
    private String firstName;
    private String lastName;
    
    @NotBlank(message = "O telefone é obrigatório")
    private String phone;

    @NotBlank(message = "O email é obrigatório")
    private String email;
    
    @NotBlank(message = "O valor da conta é obrigatório")
    private String monthlyBill;
    
    private String cityState;
    private String propertyType;
    
    @Valid
    private CreateAddressDto address;
}
