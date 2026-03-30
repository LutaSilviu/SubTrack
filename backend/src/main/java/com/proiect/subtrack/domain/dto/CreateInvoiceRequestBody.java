package com.proiect.subtrack.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateInvoiceRequestBody {

    @NotNull(message = "Subscription ID is required")
    private Long subscriptionId;
}
