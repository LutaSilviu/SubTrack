package com.proiect.subtrack.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "plans")
@SequenceGenerator(name = "plan_id_seq", sequenceName = "plan_id_seq", allocationSize = 1)
public class PlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plan_id_seq")
    @Column(name = "plan_id")
    private Long planId;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "included_gb")
    private Integer includedGb;

    @Column(name = "overage_price")
    private Double overagePrice;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "active")
    private boolean active;
}
