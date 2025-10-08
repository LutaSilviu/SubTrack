package com.proiect.subtrack.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsageRecordDto {

    private Long record_id;

    private SubscriptionDto subscriptionDto;

    private Double amount_gb;

    private Date occurred_at;

}
