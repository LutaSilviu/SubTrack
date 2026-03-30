package com.proiect.subtrack.services;

import com.proiect.subtrack.domain.dto.SubscriptionUserViewDto;
import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import com.proiect.subtrack.utils.SubscriptionStatus;

import java.util.List;


public interface SubscriptionService {

    SubscriptionEntity save(SubscriptionEntity subscriptionEntity);

     List<SubscriptionUserViewDto> getCurrent();

    // opțional: fără filtru de status
     List<SubscriptionUserViewDto> getAllForToday();

    void updateStatus(Long id, SubscriptionStatus status);

    List<SubscriptionEntity> getSubscriptionsForUser(Long userId);

    SubscriptionEntity getSubscriptionById(Long subscriptionId);

    SubscriptionEntity updateSubscription(SubscriptionEntity subscriptionEntity);
}
