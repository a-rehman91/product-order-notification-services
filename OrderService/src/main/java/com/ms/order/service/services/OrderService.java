package com.ms.order.service.services;

import com.ms.order.service.dto.OrderRequest;

public interface OrderService {

    public void placeOrder(OrderRequest orderRequest) throws IllegalAccessException;
}
