package com.atlas.backend.dto;

import java.time.LocalDateTime;

public record InventoryResponse(

        Long id,
        Long productId,
        String productSku,
        String productName,
        Long warehouseId,
        String warehouseCode,
        String warehouseName,
        int availableQuantity,
        int reservedQuantity,
        int totalQuantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
