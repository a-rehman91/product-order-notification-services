package com.ms.inventory.service.services;

import com.ms.inventory.service.dto.InventoryResponse;
import com.ms.inventory.service.model.Inventory;

import java.util.List;

public interface InventoryService {

    public List<InventoryResponse> isInStock(List<String> skuCode);
}
