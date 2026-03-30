package com.proiect.subtrack.domain.dto;

import com.proiect.subtrack.utils.SubscriptionStatus;

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

public class SubscriptionDto {

    private Long subscriptionId;

    @NotNull(message = "Plan is required")
    @Valid
    private PlanDto plan;

    @NotNull(message = "User is required")
    @Valid
    private UserDto user;

    @NotNull(message = "Status is required")
    private SubscriptionStatus status;

    private LocalDate createdAt;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    private LocalDate currentCycleStart;

    private LocalDate currentCycleStop;

}
