package com.ms.order.service.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ms.order.service.configurations.AppConstants;
import com.ms.order.service.dto.OrderRequest;
import com.ms.order.service.services.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(AppConstants.API + "/" + AppConstants.APP_VERSION + "/" + AppConstants.ORDER_BASE_URL)
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    @CircuitBreaker(name="inventoryCircuitBreaker", fallbackMethod = "placeOrderFallBack")
    @TimeLimiter(name = "inventoryCircuitBreaker", fallbackMethod = "placeOrderFallBack")
    @Retry(name = "inventoryCircuitBreaker")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) throws IllegalAccessException {

        log.info("request in controller : " + orderRequest.toString());
        return CompletableFuture.supplyAsync(() -> {
            try {
                return this.orderService.placeOrder(orderRequest);
            } catch (IllegalAccessException | JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public CompletableFuture<String> placeOrderFallBack(OrderRequest orderRequest, RuntimeException ex){

        return CompletableFuture.supplyAsync(() -> "Something went wrong, Please order after some time.");
    }
}
