package com.proiect.subtrack.services;

import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import com.proiect.subtrack.domain.entities.UsageRecordEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface UsageRecordService {

    List<UsageRecordEntity> getUsageRecordsForSubscriptionInRange(Long subscriptionId);

    UsageRecordEntity addUsage(Long id,Double consumedGb);
}
