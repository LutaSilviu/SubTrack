package com.proiect.subtrack.domain.dto;

import com.proiect.subtrack.utils.SubscriptionStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SubscriptionDto {

    private Long subscriptionId;

    private PlanDto planDto;

    private UserDto userDto;

    private SubscriptionStatus status;

    private LocalDate createdAt;

    private Integer phoneNumber;

    private LocalDate currentCycleStart;

    private LocalDate currentCycleStop;

}
