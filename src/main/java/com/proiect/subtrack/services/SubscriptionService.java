package com.proiect.subtrack.services;

import com.proiect.subtrack.domain.dto.SubscriptionUserViewDto;
import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import com.proiect.subtrack.utils.SubscriptionStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


public interface SubscriptionService {

    SubscriptionEntity save(SubscriptionEntity subscriptionEntity);

     List<SubscriptionUserViewDto> getCurrent();

    // opțional: fără filtru de status
     List<SubscriptionUserViewDto> getAllForToday();
}
