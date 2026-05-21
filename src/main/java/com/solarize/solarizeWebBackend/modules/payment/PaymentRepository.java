package com.solarize.solarizeWebBackend.modules.payment;

import com.solarize.solarizeWebBackend.modules.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
