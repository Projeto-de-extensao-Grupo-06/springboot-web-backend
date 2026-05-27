package com.solarize.solarizeWebBackend.modules.payment.model;

import com.solarize.solarizeWebBackend.modules.payment.enumerated.PaymentInstallmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentInstallment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_payment_installment")
    private Long id;

    private Integer installmentNumber;
    private Double amount;
    private LocalDate dueDate;
    
    private LocalDateTime paidAt;

    @Enumerated(EnumType.STRING)
    private PaymentInstallmentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_payment")
    private Payment payment;
}
