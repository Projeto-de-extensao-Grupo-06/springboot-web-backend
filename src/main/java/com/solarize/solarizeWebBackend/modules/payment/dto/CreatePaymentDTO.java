package com.solarize.solarizeWebBackend.modules.payment.dto;

import com.solarize.solarizeWebBackend.modules.payment.enumerated.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePaymentDTO {
    @NotNull
    private PaymentMethod method;

    @NotNull
    private Integer installmentsCount;

    @NotNull
    private Long budgetId;
}
