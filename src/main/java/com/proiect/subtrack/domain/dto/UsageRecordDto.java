package com.proiect.subtrack.domain.dto;

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
public class UsageRecordDto {

    private Long recordId;

    private SubscriptionDto subscription;

    private Double amountGb;

    private LocalDate occurredAt;

}
