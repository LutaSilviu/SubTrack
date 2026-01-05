package com.proiect.subtrack.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUsageRequestBody {

    @NotNull(message = "Subscription ID is required")
    private Long subscriptionId;

    @NotNull(message = "Start date is required")
    private LocalDate dateStart;

    @NotNull(message = "End date is required")
    private LocalDate dateEnd;
}
