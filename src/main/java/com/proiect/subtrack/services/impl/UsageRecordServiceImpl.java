package com.proiect.subtrack.services.impl;

import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import com.proiect.subtrack.domain.entities.UsageRecordEntity;
import com.proiect.subtrack.repositories.UsageRecordRepository;
import com.proiect.subtrack.services.SubscriptionService;
import com.proiect.subtrack.services.UsageRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsageRecordServiceImpl implements UsageRecordService {

    private final UsageRecordRepository usageRecordRepository;
    private final SubscriptionService subscriptionService;

    @Override
    @Transactional
    @Cacheable(value = "usageRecordsList", key = "#subscriptionId")
    public List<UsageRecordEntity> getUsageRecordsForSubscriptionInRange(Long subscriptionId) {
        log.debug("Fetching usage records for subscription ID: {}", subscriptionId);

        SubscriptionEntity subscriptionEntity = subscriptionService.getSubscriptionById(subscriptionId);

        List<UsageRecordEntity> records = usageRecordRepository
                .getUsageRecordEntitiesBySubscriptionAndOccurredAtIsBetween(subscriptionEntity,
                        subscriptionEntity.getCurrentCycleStart(),
                        subscriptionEntity.getCurrentCycleStop());

        log.info("Retrieved {} usage records for subscription ID: {}", records.size(), subscriptionId);
        return records;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"usageRecordsList", "subscriptionInvoices"}, key = "#id")
    public UsageRecordEntity addUsage(Long id, Double consumedGb) {
        log.info("Adding usage record for subscription ID: {}, consumed: {} GB", id, consumedGb);


        UsageRecordEntity usageRecordEntity = UsageRecordEntity
                .builder()
                .subscription(subscriptionService.getSubscriptionById(id))
                .occurredAt(LocalDate.now())
                .amountGb(consumedGb)
                .build();

        log.debug("Usage record phone number: {}", usageRecordEntity.getSubscription().getPhoneNumber());
        UsageRecordEntity saved = usageRecordRepository.save(usageRecordEntity);
        log.info("Usage record saved successfully for subscription ID: {}", id);
        return saved;
    }
}
