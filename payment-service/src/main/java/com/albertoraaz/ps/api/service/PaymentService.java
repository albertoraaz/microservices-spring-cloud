package com.albertoraaz.ps.api.service;

import com.albertoraaz.ps.api.entity.Payment;
import com.albertoraaz.ps.api.repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    private Logger log = LoggerFactory.getLogger(PaymentService.class);

    public Payment doPayment(Payment payment) throws JsonProcessingException {
        // generate a random number as transactionId
        payment.setTransactionId(UUID.randomUUID().toString());

        log.info("PaymentService request: {}", new ObjectMapper().writeValueAsString(payment));


        return paymentRepository.save(payment);
    }

    public Payment findPaymentByOrderId(int orderId) throws JsonProcessingException {

        Payment payment = paymentRepository.findPaymentByOrderId(orderId);

        log.info("PaymentService response: {}", new ObjectMapper().writeValueAsString(payment));

        return payment;
    }
}
