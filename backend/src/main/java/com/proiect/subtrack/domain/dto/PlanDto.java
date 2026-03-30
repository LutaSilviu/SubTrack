package com.proiect.subtrack.domain.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanDto {

    private Long planId;

    @NotBlank(message = "Plan name is required")
    @Size(min = 2, max = 100, message = "Plan name must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Included GB is required")
    @PositiveOrZero(message = "Included GB must be positive or zero")
    private Integer includedGb;

    @NotNull(message = "Overage price is required")
    @PositiveOrZero(message = "Overage price must be positive or zero")
    private Double overagePrice;

    private LocalDate createdAt;

    private boolean active;
}
