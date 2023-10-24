package com.ms.product.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.product.service.configurations.AppConstants;
import com.ms.product.service.dto.ProductRequest;
import com.ms.product.service.model.Product;
import com.ms.product.service.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

    // Make the MongoDBContainer by passing the latest dockerImageName.
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;

    // Replicating the mongodb url by using the DynamicPropertyRegistry
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {

        dynamicPropertyRegistry.add("spring.data.mongodb.url", mongoDBContainer::getReplicaSetUrl);
    }

    void createProduct(ProductRequest productRequest) throws Exception{

        String productRequestString = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8070/api/v1/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestString))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldCreateProduct() throws Exception {

        ProductRequest productRequest = getProductRequest();

        createProduct(productRequest);

        Assertions.assertEquals(1, productRepository.findAll().size());
        productRepository.deleteAll();

    }

    @Test
    void getAllProduct() throws Exception {

        ProductRequest productRequest = getProductRequest();
        createProduct(productRequest);
        createProduct(productRequest);

        List<Product> products = productRepository.findAll();

        Assertions.assertNotNull(products);
        Assertions.assertEquals(2, products.size());
        productRepository.deleteAll();

    }

    private ProductRequest getProductRequest() {

        return ProductRequest.builder()
                .name("Iphone 10")
                .description("Iphone 10 description")
                .price(BigDecimal.valueOf(876))
                .build();
    }

}
