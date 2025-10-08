package com.proiect.subtrack.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "plans")
public class PlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "plan_id_seq")
    private Long plan_id;

    private String name;

    private Double price;

    private Integer included_gb;

    private  Double overage_price;

    private Date created_at;

    private boolean is_active;
}
