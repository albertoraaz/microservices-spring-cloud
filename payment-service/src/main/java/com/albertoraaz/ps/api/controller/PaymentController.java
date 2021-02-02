package com.albertoraaz.ps.api.controller;

import com.albertoraaz.ps.api.entity.Payment;
import com.albertoraaz.ps.api.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/doPayment")
    public Payment doPayment(@RequestBody Payment payment) throws JsonProcessingException {
        payment.setPaymentStatus(paymentProcessing());
        return paymentService.doPayment(payment);
    }

    @GetMapping("/{orderId}")
    public Payment findPaymentByOrderId(@PathVariable int orderId) throws JsonProcessingException {
        return paymentService.findPaymentByOrderId(orderId);
    }

    public String paymentProcessing() {
        return new Random().nextBoolean() ? "success" : "false";
    }

}