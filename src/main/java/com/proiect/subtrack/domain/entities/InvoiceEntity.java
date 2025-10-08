package com.proiect.subtrack.domain.entities;

import com.proiect.subtrack.utils.InvoiceStatus;
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
@Table(name = "invoices")
public class InvoiceEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "invoice_id_seq")
    private Long invoice_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subscription_id")
    private SubscriptionEntity subscriptionEntity;

    private Double base_price;

    private Double overage_cost;

    private Double total;

    private Date created_at;

    private Date period_start;

    private Date period_stop;

    private InvoiceStatus status;

}
