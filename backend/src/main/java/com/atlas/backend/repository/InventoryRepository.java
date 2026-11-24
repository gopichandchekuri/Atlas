package com.atlas.backend.repository;

import com.atlas.backend.entity.Inventory;
import com.atlas.backend.entity.Product;
import com.atlas.backend.entity.Warehouse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InventoryRepository extends JpaRepository<Inventory,Long> {
        boolean existsByProductIdAndWarehouseId(Long productId,Long warehouseId);

        @EntityGraph(attributePaths = {"product", "warehouse"})
        List<Inventory> findByProductId(Long productId);

        @EntityGraph(attributePaths = {"product", "warehouse"})
        List<Inventory> findByWarehouseId(Long warehouseId);










}
