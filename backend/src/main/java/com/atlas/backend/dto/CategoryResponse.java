package com.atlas.backend.dto;

import java.time.LocalDateTime;

public record CategoryResponse(
        Long id,
        String name,
        String description,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
