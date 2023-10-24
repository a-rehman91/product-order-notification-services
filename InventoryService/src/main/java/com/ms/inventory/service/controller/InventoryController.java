package com.ms.inventory.service.controller;

import com.ms.inventory.service.configurations.AppConstants;
import com.ms.inventory.service.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstants.API + "/" + AppConstants.APP_VERSION + "/" + AppConstants.INVENTORY_BASE_URL)
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/{" + AppConstants.SKU_CODE + "}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable(AppConstants.SKU_CODE) String skuCode){

        return this.inventoryService.isInStock(skuCode);
    }
}
