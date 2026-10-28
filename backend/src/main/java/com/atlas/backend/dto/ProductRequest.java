package com.atlas.backend.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequest(@NotBlank @Size(max = 64) String sku,
                             @NotBlank @Size(max = 150) String name,
                             String description,
                             @NotNull Long categoryId,
                             @NotNull @PositiveOrZero BigDecimal price) {


}
