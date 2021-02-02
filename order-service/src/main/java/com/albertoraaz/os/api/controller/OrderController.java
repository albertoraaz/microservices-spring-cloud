package com.albertoraaz.os.api.controller;

import com.albertoraaz.os.api.common.TransactionRequest;
import com.albertoraaz.os.api.common.TransactionResponse;
import com.albertoraaz.os.api.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/BookOrders")
    public TransactionResponse saveOrder(@RequestBody TransactionRequest request) throws JsonProcessingException {
        return orderService.saveOrder(request);
    }



}
