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
public class SubscriptionUserViewDto {

    private Long subscriptionId;

    private String userName;

    private String email;

    private LocalDate dateOfBirth;

    private String planName;

    private Double price;

    private Integer includedGb;

    private  Double overagePrice;

    private SubscriptionStatus status;

    private LocalDate createdAt;

    private String phoneNumber;

    private LocalDate currentCycleStart;

    private LocalDate currentCycleStop;
}
