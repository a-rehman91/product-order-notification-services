package com.ms.inventory.service.services.impl;

import com.ms.inventory.service.repositories.InventoryRepository;
import com.ms.inventory.service.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) {

        return this.inventoryRepository.findBySkuCode().isPresent();
    }
}
