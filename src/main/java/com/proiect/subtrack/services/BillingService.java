package com.proiect.subtrack.services;

import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;

public interface BillingService {

    void calculateInvoicePerSubscription(SubscriptionEntity subscriptionEntity);

    public void runDailyBilling();
}


