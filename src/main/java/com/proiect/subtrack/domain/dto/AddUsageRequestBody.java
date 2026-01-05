package com.proiect.subtrack.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class AddUsageRequestBody {

    @NotNull(message = "Subscription ID is required")
    private Long id;

    @NotNull(message = "Usage amount is required")
    @Positive(message = "Usage must be positive")
    private Double usageGb;
}
