package com.solarize.solarizeWebBackend.modules.payment;

import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import com.solarize.solarizeWebBackend.modules.budget.model.Budget;
import com.solarize.solarizeWebBackend.modules.budget.repository.BudgetRepository;
import com.solarize.solarizeWebBackend.modules.payment.dto.CreatePaymentDTO;
import com.solarize.solarizeWebBackend.modules.payment.enumerated.PaymentInstallmentStatus;
import com.solarize.solarizeWebBackend.modules.payment.model.Payment;
import com.solarize.solarizeWebBackend.modules.payment.model.PaymentInstallment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentInstallmentRepository installmentRepository;
    private final BudgetRepository budgetRepository;

    public Payment createPayment(CreatePaymentDTO dto) {
        Budget budget = budgetRepository.findById(dto.getBudgetId())
                .orElseThrow(() -> new NotFoundException("Budget not found"));
        
        Double totalAmount = budget.getTotalCost(); // Using totalCost from Budget

        Payment payment = Payment.builder()
                .method(dto.getMethod())
                .installmentsCount(dto.getInstallmentsCount())
                .totalAmount(totalAmount)
                .budget(budget)
                .build();

        List<PaymentInstallment> installments = new ArrayList<>();
        double installmentAmount = totalAmount / dto.getInstallmentsCount();

        for (int i = 1; i <= dto.getInstallmentsCount(); i++) {
            installments.add(PaymentInstallment.builder()
                    .payment(payment)
                    .installmentNumber(i)
                    .amount(installmentAmount)
                    .dueDate(LocalDate.now().plusMonths(i - 1))
                    .status(PaymentInstallmentStatus.PENDING)
                    .build());
        }
        
        payment.setInstallments(installments);
        return paymentRepository.save(payment);
    }

    public Payment getPaymentByBudgetId(Long budgetId) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new NotFoundException("Budget not found"));
        return budget.getPayment();
    }

    public PaymentInstallment markInstallmentAsPaid(Long installmentId) {
        PaymentInstallment installment = installmentRepository.findById(installmentId)
                .orElseThrow(() -> new NotFoundException("Installment not found"));
        
        installment.setStatus(PaymentInstallmentStatus.PAID);
        installment.setPaidAt(LocalDateTime.now());
        return installmentRepository.save(installment);
    }
}
