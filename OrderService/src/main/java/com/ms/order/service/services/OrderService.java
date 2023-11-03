package com.ms.order.service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ms.order.service.dto.OrderRequest;

public interface OrderService {

    String placeOrder(OrderRequest orderRequest) throws IllegalAccessException, JsonProcessingException;
}
