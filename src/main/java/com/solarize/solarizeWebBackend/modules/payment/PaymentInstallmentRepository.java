package com.solarize.solarizeWebBackend.modules.payment;

import com.solarize.solarizeWebBackend.modules.payment.model.PaymentInstallment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentInstallmentRepository extends JpaRepository<PaymentInstallment, Long> {
    List<PaymentInstallment> findByPaymentIdOrderByInstallmentNumberAsc(Long paymentId);
}
