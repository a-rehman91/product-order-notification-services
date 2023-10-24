package com.ms.product.service.services;

import com.ms.product.service.dto.ProductRequest;
import com.ms.product.service.dto.ProductResponse;
import com.ms.product.service.model.Product;
import com.ms.product.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface ProductService {

    ProductResponse create(ProductRequest productRequest);
    List<ProductResponse> getAllProducts();

    void deleteAll();
}
