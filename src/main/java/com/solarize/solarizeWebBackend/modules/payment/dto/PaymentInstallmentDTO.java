package com.solarize.solarizeWebBackend.modules.payment.dto;

import com.solarize.solarizeWebBackend.modules.payment.enumerated.PaymentInstallmentStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentInstallmentDTO {
    private Long id;
    private Integer installmentNumber;
    private Double amount;
    private LocalDate dueDate;
    private LocalDateTime paidAt;
    private PaymentInstallmentStatus status;
}
