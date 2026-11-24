package com.atlas.backend.repository;

import com.atlas.backend.entity.Inventory;
import com.atlas.backend.entity.Product;
import com.atlas.backend.entity.Warehouse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface InventoryRepository extends JpaRepository<Inventory,Long> {
        boolean existsByProductIdAndWarehouseId(Long productId,Long warehouseId);

        @EntityGraph(attributePaths = {"product", "warehouse"})
        List<Inventory> findByProductId(Long productId);

        @EntityGraph(attributePaths = {"product", "warehouse"})
        List<Inventory> findByWarehouseId(Long warehouseId);


        @Query("select count(i) from Inventory i "
                + "where i.warehouse.id = :warehouseId "
                + "and (i.availableQuantity > 0 or i.reservedQuantity > 0)")
        long countStockedLinesByWarehouseId(@Param("warehouseId") Long warehouseId);











}
