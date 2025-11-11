package com.proiect.subtrack.services.impl;

import com.proiect.subtrack.domain.dto.SubscriptionUserViewDto;
import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import com.proiect.subtrack.repositories.SubscriptionRepository;
import com.proiect.subtrack.services.SubscriptionService;
import com.proiect.subtrack.utils.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    final private SubscriptionRepository subscriptionRepository;

    @Override
    public SubscriptionEntity save(SubscriptionEntity subscriptionEntity) {
        return subscriptionRepository.save(subscriptionEntity);
    }

    public List<SubscriptionUserViewDto> getCurrent() {
        return subscriptionRepository.findCurrentSubscriptions(LocalDate.now(), SubscriptionStatus.ACTIVE);
    }

    // opțional: fără filtru de status
    public List<SubscriptionUserViewDto> getAllForToday() {
        return subscriptionRepository.findCurrentSubscriptions(LocalDate.now(), null);
    }
}
