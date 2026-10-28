package com.atlas.backend.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String sku,
        String name,
        String description,
        Long categoryId,
        String categoryName,
        BigDecimal price,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
