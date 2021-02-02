package com.albertoraaz.os.api.service;

import com.albertoraaz.os.api.common.Payment;
import com.albertoraaz.os.api.common.TransactionRequest;
import com.albertoraaz.os.api.common.TransactionResponse;
import com.albertoraaz.os.api.entity.Order;
import com.albertoraaz.os.api.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    @Lazy
    private RestTemplate restTemplate;

    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String ENDPOINT_URL;

    private Logger log = LoggerFactory.getLogger(OrderService.class);

    // method that integrate order with payment
    public TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException {

        String response = "";
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());

        // log the request
        log.info("OrderService request: {}", new ObjectMapper().writeValueAsString(request));
        // rest call
        Payment paymentResponse = restTemplate.postForObject(ENDPOINT_URL, payment, Payment.class);

        response = paymentResponse.getPaymentStatus().equals("success") ? "payment processed and order placed" : "failed in payment";
        orderRepository.save(order);

        // log the response
        log.info("PaymentService response from : {}", new ObjectMapper().writeValueAsString(response));

        return new TransactionResponse(order, payment.getAmount(), paymentResponse.getTransactionId(), response);
    }
}