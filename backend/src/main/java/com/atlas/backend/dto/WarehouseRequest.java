package com.atlas.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record WarehouseRequest(


        @NotBlank(message = "Code is required")
        @Size(max = 30, message = "Code must be at most 30 characters")
        @Pattern(regexp = "^[A-Za-z0-9-]*$",
                message = "Code may contain only letters, digits and hyphens")
        String code,


        @NotBlank(message = "Name is required")
        @Size(max = 150, message = "Name must be at most 150 characters")
        String name,


        @NotBlank(message = "Location is required")
        @Size(max = 255, message = "Location must be at most 255 characters")
        String location




) {
}
