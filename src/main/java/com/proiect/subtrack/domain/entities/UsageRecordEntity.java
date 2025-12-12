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
@Table(name = "usage_records")
@SequenceGenerator(name = "records_id_seq", sequenceName = "records_id_seq", allocationSize = 1)
public class UsageRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "records_id_seq")
    @Column(name = "record_id")
    private Long recordId;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private SubscriptionEntity subscription;

    @Column(name = "amount_gb")
    private Double amountGb;

    @Column(name = "occurred_at")
    private LocalDate occurredAt;

}
