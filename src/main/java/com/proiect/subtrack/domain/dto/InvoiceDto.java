package com.proiect.subtrack.domain.dto;

import com.proiect.subtrack.utils.InvoiceStatus;
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

public class InvoiceDto {


    private Long invoiceId;

    private SubscriptionDto subscription;

    private Double basePrice;

    private Double overageCost;

    private Double total;

    private LocalDate createdAt;

    private LocalDate periodStart;

    private Date periodStop;

    private InvoiceStatus status;

}
