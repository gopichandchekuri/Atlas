package com.atlas.backend.service.impl;

import com.atlas.backend.dto.WarehouseRequest;
import com.atlas.backend.dto.WarehouseResponse;
import com.atlas.backend.dto.WarehouseStatusRequest;
import com.atlas.backend.entity.Warehouse;
import com.atlas.backend.exception.DuplicateResourceException;
import com.atlas.backend.exception.ResourceNotFoundException;
import com.atlas.backend.repository.WarehouseRepository;
import com.atlas.backend.service.WarehouseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class WarehouseServiceImpl implements WarehouseService {


    private final WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository){
        this.warehouseRepository=warehouseRepository;
    }

    @Override
    @Transactional
    public WarehouseResponse createWarehouse(WarehouseRequest request) {
        String normalizedCode=normalizeCode(request.code());

        if(warehouseRepository.existsByCode(normalizedCode)){
            throw new DuplicateResourceException("Warehouse code already exists: " + normalizedCode);
        }

        Warehouse warehouse = new Warehouse();
        copyRequestToWarehouse(request,warehouse);
        warehouse.setCode(normalizedCode);
        warehouse.setStatus("ACTIVE");
        Warehouse savedWarehouse=warehouseRepository.save(warehouse);

        return toResponse(savedWarehouse);


    }

    @Override
    @Transactional(readOnly = true)
    public List<WarehouseResponse> getAllWarehouses() {
        return warehouseRepository.findAll().stream().map(this::toResponse).toList();
    }


    @Override
    public WarehouseResponse getWarehouseById(Long warehouseId) {
       Warehouse warehouse=findWarehouseOrThrow(warehouseId);
       return toResponse(warehouse);


    }

    @Override
    public WarehouseResponse updateWarehouse(Long warehouseId, WarehouseRequest request) {
        Warehouse warehouse=findWarehouseOrThrow(warehouseId);
        String normalizedCode=normalizeCode(request.code());
        if(warehouseRepository.existsByCodeAndIdNot(normalizedCode,warehouseId)){
            throw new DuplicateResourceException("Warehouse already exists: " + normalizedCode);
        }

        copyRequestToWarehouse(request,warehouse);
        warehouse.setCode(normalizedCode);
        return toResponse(warehouse);

    }

    @Override
    public WarehouseResponse updateWarehouseStatus(Long warehouseId, WarehouseStatusRequest request) {
        Warehouse warehouse= findWarehouseOrThrow(warehouseId);
        warehouse.setStatus(request.status());
        return toResponse(warehouse);
    }

    @Override
    public void deleteWarehouseById(Long warehouseId) {
        Warehouse warehouse= findWarehouseOrThrow(warehouseId);
        warehouse.setStatus("INACTIVE");

    }


    private String normalizeCode(String code){
        return code.trim().toUpperCase(Locale.ROOT);
    }

    private Warehouse findWarehouseOrThrow(Long warehouseId){
        return warehouseRepository.findById(warehouseId).orElseThrow(()->new ResourceNotFoundException("Warehouse not found: " + warehouseId));

    }

    private void copyRequestToWarehouse(WarehouseRequest request, Warehouse warehouse){
        warehouse.setCode(request.code());
        warehouse.setName(request.name().trim());
        warehouse.setLocation(request.location().trim());

    }

    private WarehouseResponse toResponse(Warehouse warehouse){
        return  new WarehouseResponse(
                warehouse.getId(),
                warehouse.getCode(),
                warehouse.getName(),
                warehouse.getLocation(),
                warehouse.getStatus(),
                warehouse.getCreatedAt(),
                warehouse.getUpdatedAt());



    }




}
