package com.proiect.subtrack.domain.dto;

import com.proiect.subtrack.utils.InvoiceStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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

    @NotNull(message = "Subscription is required")
    @Valid
    private SubscriptionDto subscription;

    @NotNull(message = "Base price is required")
    @PositiveOrZero(message = "Base price must be positive or zero")
    private Double basePrice;

    @NotNull(message = "Overage cost is required")
    @PositiveOrZero(message = "Overage cost must be positive or zero")
    private Double overageCost;

    @NotNull(message = "Total is required")
    @PositiveOrZero(message = "Total must be positive or zero")
    private Double total;

    private LocalDate createdAt;

    @NotNull(message = "Period start is required")
    private LocalDate periodStart;

    @NotNull(message = "Period stop is required")
    private Date periodStop;

    @NotNull(message = "Status is required")
    private InvoiceStatus status;

}
