package com.ms.order.service.services.impl;

import com.ms.order.service.dto.InventoryResponse;
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
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
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
    @Autowired
    private WebClient webClient;

    @Override
    public void placeOrder(OrderRequest orderRequest) throws IllegalAccessException {

        String orderId = UUID.randomUUID().toString();

        log.info("order request : ", orderRequest.getOrderLineItemsDtos());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtos()
                .stream()
                .map(orderLineItemsDto -> modelMapper.map(orderLineItemsDto, OrderLineItems.class))
                .collect(Collectors.toList());

        Order order = Order.builder()
                .orderNumber(orderId)
                .orderLineItemList(orderLineItems)
                .build();

        List<String> skuCodes = order.getOrderLineItemList()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        //Call inventory service, and place order if product is in stock.

        InventoryResponse[] inventoryResponses = this.webClient.get()
                .uri("http://localhost:8068/api/v1/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if(allProductsInStock)
            this.orderRepository.save(order);
        else
            throw new IllegalAccessException("Product is not in stock, please try again later.");

    }
}
