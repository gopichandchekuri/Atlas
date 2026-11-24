package com.atlas.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record StockAdjustmentRequest(


        @NotNull(message = "Quantity change is required")
        @Min(value = -1_000_000, message = "Quantity change cannot be below -1,000,000")
        @Max(value = 1_000_000, message = "Quantity change cannot exceed 1,000,000")
        Integer quantityChange
) {
}
