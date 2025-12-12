package com.proiect.subtrack.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUsageRequestBody {

    Long subscriptionId;

    LocalDate dateStart;

    LocalDate dateEnd;
}
