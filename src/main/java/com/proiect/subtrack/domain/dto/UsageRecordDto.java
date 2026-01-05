package com.proiect.subtrack.domain.dto;

import jakarta.validation.Valid;
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
public class UsageRecordDto {

    private Long recordId;

    @NotNull(message = "Subscription is required")
    @Valid
    private SubscriptionDto subscription;

    @NotNull(message = "Amount in GB is required")
    @Positive(message = "Amount must be positive")
    private Double amountGb;

    @NotNull(message = "Occurred date is required")
    @PastOrPresent(message = "Occurred date must be in the past or present")
    private LocalDate occurredAt;

}
