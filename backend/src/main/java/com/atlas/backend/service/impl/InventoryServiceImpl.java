package com.atlas.backend.service.impl;

import com.atlas.backend.dto.InventoryRequest;
import com.atlas.backend.dto.InventoryResponse;
import com.atlas.backend.dto.StockAdjustmentRequest;
import com.atlas.backend.entity.Inventory;
import com.atlas.backend.entity.Product;
import com.atlas.backend.entity.Warehouse;
import com.atlas.backend.exception.BusinessRuleException;
import com.atlas.backend.exception.DuplicateResourceException;
import com.atlas.backend.exception.ResourceNotFoundException;
import com.atlas.backend.repository.InventoryRepository;
import com.atlas.backend.repository.ProductRepository;
import com.atlas.backend.repository.WarehouseRepository;
import com.atlas.backend.service.InventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private static final String STATUS_ACTIVE = "ACTIVE";

    private static final int MAX_STOCK_PER_LINE = 100_000_000;

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository,
                                ProductRepository productRepository,
                                WarehouseRepository warehouseRepository){

        this.inventoryRepository=inventoryRepository;
        this.productRepository=productRepository;
        this.warehouseRepository=warehouseRepository;
    }

    @Override
    @Transactional
    public InventoryResponse createInventory(InventoryRequest request) {
        Product product=findProductOrThrow(request.productId());
        Warehouse warehouse=findWarehouseOrThrow(request.warehouseId());

        if(!STATUS_ACTIVE.equals(product.getStatus())){
            throw new BusinessRuleException("Cannot add stock for an inactive product:"+product.getSku());

        }

        if(!STATUS_ACTIVE.equals(warehouse.getStatus())){
            throw new BusinessRuleException("Cannot add stock for an inactive warehouse:"+warehouse.getCode());
        }

        if(inventoryRepository.existsByProductIdAndWarehouseId(product.getId(), warehouse.getId())){
            throw new DuplicateResourceException("Stock line already exists for product "
                    + product.getSku() + " in warehouse " + warehouse.getCode());
        }

        Inventory inventory=new Inventory();
        inventory.setProduct(product);
        inventory.setWarehouse(warehouse);
        inventory.setAvailableQuantity(request.availableQuantity());
        inventory.setReservedQuantity(0);
        Inventory savedInventory=inventoryRepository.save(inventory);
        return toResponse(savedInventory);

    }


    @Override
    @Transactional
    public InventoryResponse adjustStock(Long inventoryId,StockAdjustmentRequest request) {

        Inventory inventory=findInventoryOrThrow(inventoryId);
        long currentAvailable=inventory.getAvailableQuantity();
        long newAvailable=currentAvailable+request.quantityChange();

        if(newAvailable<0){
            throw new BusinessRuleException("Not enough stock: available "+currentAvailable+", requested change"+request.quantityChange());
        }

        if(newAvailable>MAX_STOCK_PER_LINE){
            throw new BusinessRuleException("Stock per line cannot exceed " + MAX_STOCK_PER_LINE);
        }
        inventory.setAvailableQuantity((int)newAvailable);
        Inventory savedInventory=inventoryRepository.saveAndFlush(inventory);
        return toResponse(savedInventory);

    }

    @Override
    @Transactional(readOnly = true)
    public InventoryResponse getInventoryById(Long inventoryId) {
      Inventory inventory=findInventoryOrThrow(inventoryId);
      return toResponse(inventory);
    }


    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getInventoryByProductId(Long productId) {

        if(!productRepository.existsById(productId)){
            throw new ResourceNotFoundException("Product not found: "+productId);
        }
        return inventoryRepository.findByProductId(productId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getInventoryByWarehouseId(Long warehouseId) {
        if(!warehouseRepository.existsById(warehouseId)){
            throw new ResourceNotFoundException("warehouse not found: "+warehouseId);
        }

        return inventoryRepository.findByWarehouseId(warehouseId)
                                  .stream()
                                  .map(this::toResponse)
                                  .toList();
    }


   private Inventory findInventoryOrThrow(Long inventoryId){
        return inventoryRepository.findById(inventoryId).orElseThrow(()->new ResourceNotFoundException("Inventory Line not found:"+inventoryId));
    }

    private Product findProductOrThrow(Long productId){
        return productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not found: "+productId));

    }

    private Warehouse findWarehouseOrThrow(Long warehouseId){
        return warehouseRepository.findById(warehouseId).orElseThrow(()->new ResourceNotFoundException("Warehouse not found: " + warehouseId));

    }

    private InventoryResponse toResponse(Inventory inventory) {

        Product product=inventory.getProduct();
        Warehouse warehouse=inventory.getWarehouse();

        return new InventoryResponse(

        inventory.getId(),
        product.getId(),
        product.getSku(),
        product.getName(),
        warehouse.getId(),
        warehouse.getCode(),
        warehouse.getName(),
        inventory.getAvailableQuantity(),
        inventory.getReservedQuantity(),
        inventory.getAvailableQuantity()+inventory.getReservedQuantity(),
        inventory.getCreatedAt(),
        inventory.getUpdatedAt()
        );

    }


}
