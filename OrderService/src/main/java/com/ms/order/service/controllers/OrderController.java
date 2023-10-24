package com.ms.order.service.controllers;

import com.ms.order.service.configurations.AppConstants;
import com.ms.order.service.dto.OrderRequest;
import com.ms.order.service.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppConstants.API + "/" + AppConstants.APP_VERSION + "/" + AppConstants.ORDER_BASE_URL)
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    public String placeOrder(@RequestBody OrderRequest orderRequest){

        log.info("request in controller : " + orderRequest.toString());
        this.orderService.placeOrder(orderRequest);
        log.info("Order Placed Successfully.");
        return "Order Placed Successfully.";
    }
}
