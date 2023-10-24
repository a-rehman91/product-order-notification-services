package com.ms.product.service.controllers;

import com.ms.product.service.configurations.AppConstants;
import com.ms.product.service.dto.ProductRequest;
import com.ms.product.service.dto.ProductResponse;
import com.ms.product.service.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(AppConstants.API + "/" + AppConstants.APP_VERSION + "/" + AppConstants.PRODUCT_BASE_URL)
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest product){

        log.info("calling the create method....");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.productService.create(product));
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductResponse>> getAllProduct(){

        log.info(this.productService+"...");
        return ResponseEntity.ok(this.productService.getAllProducts());
    }

    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll(){

        log.info("Deleting all products");
        this.productService.deleteAll();
    }
}
