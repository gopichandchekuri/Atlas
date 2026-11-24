package com.atlas.backend.dto;

import com.atlas.backend.entity.Product;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record InventoryRequest(


        @NotNull(message = "Product id is required")
        @Positive(message = "Product id must be positive")
        Long productId,


        @NotNull(message = "warehouse id is required")
        @Positive(message = "warehouse id must be positive")
        Long warehouseId,


        @NotNull(message = "Available quantity is required")
        @PositiveOrZero(message = "Available quantity cannot be negative")
        @Max(value = 1_000_000, message = "Available quantity cannot exceed 1,000,000")
        Integer availableQuantity



) {
}
