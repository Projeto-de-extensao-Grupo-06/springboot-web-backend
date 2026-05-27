package com.solarize.solarizeWebBackend.modules.payment;

import com.solarize.solarizeWebBackend.modules.payment.dto.PaymentDTO;
import com.solarize.solarizeWebBackend.modules.payment.dto.PaymentInstallmentDTO;
import com.solarize.solarizeWebBackend.modules.payment.model.Payment;
import com.solarize.solarizeWebBackend.modules.payment.model.PaymentInstallment;

import java.util.stream.Collectors;

public class PaymentMapper {
    public static PaymentDTO toDto(Payment payment) {
        if (payment == null) return null;
        return PaymentDTO.builder()
                .id(payment.getId())
                .method(payment.getMethod())
                .installmentsCount(payment.getInstallmentsCount())
                .totalAmount(payment.getTotalAmount())
                .createdAt(payment.getCreatedAt())
                .budgetId(payment.getBudget() != null ? payment.getBudget().getId() : null)
                .installments(payment.getInstallments() != null ? payment.getInstallments().stream().map(PaymentMapper::toDto).collect(Collectors.toList()) : null)
                .build();
    }

    public static PaymentInstallmentDTO toDto(PaymentInstallment installment) {
        if (installment == null) return null;
        return PaymentInstallmentDTO.builder()
                .id(installment.getId())
                .installmentNumber(installment.getInstallmentNumber())
                .amount(installment.getAmount())
                .dueDate(installment.getDueDate())
                .paidAt(installment.getPaidAt())
                .status(installment.getStatus())
                .build();
    }
}
