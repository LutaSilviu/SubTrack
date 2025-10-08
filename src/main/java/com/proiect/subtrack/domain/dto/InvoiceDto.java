package com.proiect.subtrack.domain.dto;

import com.proiect.subtrack.utils.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class InvoiceDto {


    private Long invoice_id;

    private SubscriptionDto subscriptionEntity;

    private Double base_price;

    private Double overage_cost;

    private Double total;

    private Date created_at;

    private Date period_start;

    private Date period_stop;

    private InvoiceStatus status;

}
