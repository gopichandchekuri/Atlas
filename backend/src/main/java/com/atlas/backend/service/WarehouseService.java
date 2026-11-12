package com.atlas.backend.service;

import com.atlas.backend.dto.WarehouseRequest;
import com.atlas.backend.dto.WarehouseResponse;
import com.atlas.backend.dto.WarehouseStatusRequest;
import com.atlas.backend.entity.Warehouse;
import org.springframework.stereotype.Service;

import java.util.List;


public interface WarehouseService {
    WarehouseResponse createWarehouse(WarehouseRequest request);
    List<WarehouseResponse> getAllWarehouses();
    WarehouseResponse getWarehouseById(Long warehouseId);
    WarehouseResponse updateWarehouse( Long warehouseId,WarehouseRequest request);
    WarehouseResponse updateWarehouseStatus(Long warehouseId, WarehouseStatusRequest request);
    void deleteWarehouseById(Long warehouseId);


}
