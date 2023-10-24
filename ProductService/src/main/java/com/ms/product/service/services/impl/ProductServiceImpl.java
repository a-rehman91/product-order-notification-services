package com.ms.product.service.services.impl;

import com.ms.product.service.dto.ProductRequest;
import com.ms.product.service.dto.ProductResponse;
import com.ms.product.service.model.Product;
import com.ms.product.service.repository.ProductRepository;
import com.ms.product.service.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ProductResponse create(ProductRequest productRequest) {

        String productId = UUID.randomUUID().toString();

        log.info("Product id generated : {}", productId);
        Product product = Product.builder()
                .id(productId)
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        this.productRepository.save(product);
        log.info("Product {} is saved", product.getName());

        return modelMapper.map(product, ProductResponse.class);
    }


    public List<ProductResponse> getAllProducts() {

        List<Product> products = this.productRepository
                .findAll();
        log.info("List of products : {}", products.size());
        List<ProductResponse> productsResponse = products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());

        return productsResponse;
    }

    @Override
    public void deleteAll() {

        this.productRepository.deleteAll();
    }
}
