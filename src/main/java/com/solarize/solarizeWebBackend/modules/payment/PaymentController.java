package com.solarize.solarizeWebBackend.modules.payment;

import com.solarize.solarizeWebBackend.modules.payment.dto.CreatePaymentDTO;
import com.solarize.solarizeWebBackend.modules.payment.dto.PaymentDTO;
import com.solarize.solarizeWebBackend.modules.payment.dto.PaymentInstallmentDTO;
import com.solarize.solarizeWebBackend.modules.payment.model.Payment;
import com.solarize.solarizeWebBackend.modules.payment.model.PaymentInstallment;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @PreAuthorize("hasAuthority('BUDGET_WRITE')")
    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody @Valid CreatePaymentDTO dto) {
        Payment payment = paymentService.createPayment(dto);
        return ResponseEntity.status(201).body(PaymentMapper.toDto(payment));
    }

    @PreAuthorize("hasAuthority('BUDGET_READ')")
    @GetMapping("/budget/{budgetId}")
    public ResponseEntity<PaymentDTO> getPaymentByBudget(@PathVariable Long budgetId) {
        Payment payment = paymentService.getPaymentByBudgetId(budgetId);
        if (payment == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(PaymentMapper.toDto(payment));
    }

    @PreAuthorize("hasAuthority('BUDGET_WRITE')")
    @PatchMapping("/installments/{id}/pay")
    public ResponseEntity<PaymentInstallmentDTO> markAsPaid(@PathVariable Long id) {
        PaymentInstallment installment = paymentService.markInstallmentAsPaid(id);
        return ResponseEntity.ok(PaymentMapper.toDto(installment));
    }
}
