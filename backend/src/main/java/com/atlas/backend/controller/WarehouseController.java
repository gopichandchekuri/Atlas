package com.atlas.backend.controller;

import com.atlas.backend.dto.WarehouseRequest;
import com.atlas.backend.dto.WarehouseResponse;
import com.atlas.backend.dto.WarehouseStatusRequest;
import com.atlas.backend.entity.Warehouse;
import com.atlas.backend.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService){
        this.warehouseService=warehouseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WarehouseResponse createWarehouse(@Valid @RequestBody WarehouseRequest request){
        return warehouseService.createWarehouse(request);
    }

    @GetMapping
    public List<WarehouseResponse> getAllWarehouses(){
        return warehouseService.getAllWarehouses();
    }

    @GetMapping("/{warehouseId}")
    public WarehouseResponse getWarehouseById(@PathVariable Long warehouseId){
        return warehouseService.getWarehouseById(warehouseId);
    }

    @PutMapping("/{warehouseId}")
    public WarehouseResponse updateWarehouse(@PathVariable Long warehouseId,@Valid @RequestBody WarehouseRequest request){
        return warehouseService.updateWarehouse(warehouseId, request);
    }


    @PatchMapping("/{warehouseId}/status")
    public WarehouseResponse updateWarehouseStatus(@PathVariable Long warehouseId,@Valid @RequestBody WarehouseStatusRequest request){
        return warehouseService.updateWarehouseStatus(warehouseId,request);
    }

    @DeleteMapping("/{warehouseId}")
    public void deleteWarehouseById(@PathVariable Long warehouseId){
        warehouseService.deleteWarehouseById(warehouseId);
    }


}
