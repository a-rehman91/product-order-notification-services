package com.ms.order.service.services.impl;

import com.ms.order.service.dto.OrderLineItemsDto;
import com.ms.order.service.dto.OrderRequest;
import com.ms.order.service.model.Order;
import com.ms.order.service.model.OrderLineItems;
import com.ms.order.service.repositories.OrderRepository;
import com.ms.order.service.services.OrderService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void placeOrder(OrderRequest orderRequest) {

        String orderId = UUID.randomUUID().toString();

        log.info("order request : ", orderRequest.getOrderLineItemsDtos());
        List<OrderLineItems> orderLineItemsStream = orderRequest.getOrderLineItemsDtos()
                                                            .stream()
                                                            .map(orderLineItemsDto -> modelMapper.map(orderLineItemsDto, OrderLineItems.class))
                                                            .collect(Collectors.toList());

        Order order = Order.builder()
                .orderNumber(orderId)
                .orderLineItemList(orderLineItemsStream)
                .build();

        this.orderRepository.save(order);

    }
}
