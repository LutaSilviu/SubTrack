package com.proiect.subtrack.domain.entities;

import com.proiect.subtrack.utils.SubscriptionStatus;
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
@Table(name = "subscriptions")
public class SubscriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "subscription_id_seq")
    private Long subscription_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "plan_id")
    private PlanEntity planEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    private SubscriptionStatus status;

    private Date created_at;

    private Integer phone_number;

    private Date current_cycle_start;

    private Date current_cycle_stop;

}
