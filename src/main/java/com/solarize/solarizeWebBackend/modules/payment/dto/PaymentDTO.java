package com.solarize.solarizeWebBackend.modules.payment.dto;

import com.solarize.solarizeWebBackend.modules.payment.enumerated.PaymentMethod;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PaymentDTO {
    private Long id;
    private PaymentMethod method;
    private Integer installmentsCount;
    private Double totalAmount;
    private LocalDateTime createdAt;
    private Long budgetId;
    private List<PaymentInstallmentDTO> installments;
}
