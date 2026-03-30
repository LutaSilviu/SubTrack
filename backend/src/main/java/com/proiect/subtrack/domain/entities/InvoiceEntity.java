package com.proiect.subtrack.domain.entities;

import com.proiect.subtrack.utils.InvoiceStatus;
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
@Table(name = "invoices")
@SequenceGenerator(name = "invoice_id_seq", sequenceName = "invoice_id_seq", allocationSize = 1)
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_id_seq")
    @Column(name = "invoice_id")
    private Long invoiceId;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private SubscriptionEntity subscription;

    @Column(name = "base_price")
    private Double basePrice;

    @Column(name = "overage_cost")
    private Double overageCost;

    @Column(name = "total")
    private Double total;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "period_start")
    private LocalDate periodStart;

    @Column(name = "period_stop")
    private LocalDate periodStop;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InvoiceStatus status;

}
