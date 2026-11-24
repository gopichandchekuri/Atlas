package com.atlas.backend.controller;
import com.atlas.backend.dto.InventoryRequest;
import com.atlas.backend.dto.InventoryResponse;
import com.atlas.backend.dto.StockAdjustmentRequest;
import com.atlas.backend.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService){
        this.inventoryService=inventoryService;
    }

   @PostMapping("/inventory")
   @ResponseStatus(HttpStatus.CREATED)
   public InventoryResponse createInventory(@Valid @RequestBody InventoryRequest request){
        return inventoryService.createInventory(request);

    }


    @PatchMapping("/inventory/{inventoryId}/adjustment")
    public InventoryResponse adjustStock(@PathVariable Long inventoryId,@Valid @RequestBody StockAdjustmentRequest request){
        return inventoryService.adjustStock(inventoryId,request);
    }


    @GetMapping("/inventory/{inventoryId}")
    public InventoryResponse getInventoryById(@PathVariable Long inventoryId){
        return inventoryService.getInventoryById(inventoryId);

    }

    @GetMapping("/products/{productId}/inventory")
    public List<InventoryResponse> getInventoryByProductId(@PathVariable Long productId){
        return inventoryService.getInventoryByProductId(productId);
    }


    @GetMapping("warehouses/{warehouseId}/inventory")
    public List<InventoryResponse> getInventoryByWarehouseId(@PathVariable Long warehouseId){
        return inventoryService.getInventoryByWarehouseId(warehouseId);
    }



}
