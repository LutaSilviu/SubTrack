package com.proiect.subtrack.domain.entities;

import com.proiect.subtrack.utils.SubscriptionStatus;
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
@Table(name = "subscriptions")
@SequenceGenerator(name = "subscription_id_seq", sequenceName = "subscription_id_seq", allocationSize = 1)
public class SubscriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscription_id_seq")
    @Column(name = "subscription_id")
    private Long subscriptionId;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private PlanEntity planEntity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SubscriptionStatus status;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "phone_number")
    private Integer phoneNumber;

    @Column(name = "current_cycle_start")
    private LocalDate currentCycleStart;

    @Column(name = "current_cycle_stop")
    private LocalDate currentCycleStop;
}
