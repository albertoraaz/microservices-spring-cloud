package com.albertoraaz.ps.api.repository;

import com.albertoraaz.ps.api.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Payment findPaymentByOrderId(int orderId);
}
