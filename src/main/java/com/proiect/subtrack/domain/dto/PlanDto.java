package com.proiect.subtrack.domain.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanDto {

    private Long plan_id;

    private String name;

    private Double price;

    private Integer included_gb;

    private  Double overage_price;

    private Date created_at;

    private boolean is_active;
}
