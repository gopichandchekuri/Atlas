package com.atlas.backend.service;

import com.atlas.backend.dto.InventoryRequest;
import com.atlas.backend.dto.InventoryResponse;
import com.atlas.backend.dto.StockAdjustmentRequest;
import com.atlas.backend.entity.Inventory;

import java.util.List;

public interface InventoryService {
    InventoryResponse createInventory(InventoryRequest request);

    InventoryResponse adjustStock(Long inventoryId,StockAdjustmentRequest request) ;

    InventoryResponse getInventoryById(Long inventoryId);

    List<InventoryResponse> getInventoryByProductId(Long productId);

    List<InventoryResponse> getInventoryByWarehouseId(Long warehouseId);




}
