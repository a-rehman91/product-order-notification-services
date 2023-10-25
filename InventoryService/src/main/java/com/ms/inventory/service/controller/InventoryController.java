package com.ms.inventory.service.controller;

import com.ms.inventory.service.configurations.AppConstants;
import com.ms.inventory.service.dto.InventoryResponse;
import com.ms.inventory.service.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppConstants.API + "/" + AppConstants.APP_VERSION + "/" + AppConstants.INVENTORY_BASE_URL)
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){

        return this.inventoryService.isInStock(skuCode);
    }
}
