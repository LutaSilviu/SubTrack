package com.proiect.subtrack.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "usage_records")
public class UsageRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "records_id_seq")
    private Long record_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subscription_id")
    private SubscriptionEntity subscriptionEntity;

    private Double amount_gb;

    private Date occurred_at;

}
