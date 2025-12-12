package com.proiect.subtrack.domain.dto;

import com.proiect.subtrack.utils.SubscriptionStatus;

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

    private PlanDto plan;

    private UserDto user;

    private SubscriptionStatus status;

    private LocalDate createdAt;

    private String phoneNumber;

    private LocalDate currentCycleStart;

    private LocalDate currentCycleStop;

}
