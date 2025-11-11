package com.proiect.subtrack.utils;

import com.proiect.subtrack.domain.entities.InvoiceEntity;
import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import com.proiect.subtrack.domain.entities.UsageRecordEntity;
import com.proiect.subtrack.repositories.SubscriptionRepository;
import com.proiect.subtrack.repositories.UsageRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class SimulateConsumption {

    final private UsageRecordRepository usageRecordRepository;
    final private SubscriptionRepository subscriptionRepository;

    @Scheduled(cron = "0 55 23 * * *", zone = "Europe/Bucharest")
    private void simulateConsumption(){
        List<SubscriptionEntity> subscriptionEntities = StreamSupport.stream(subscriptionRepository.findByStatus(SubscriptionStatus.ACTIVE).spliterator(), false).toList();

        for( SubscriptionEntity entry : subscriptionEntities){

            UsageRecordEntity usageRecordEntity =  UsageRecordEntity.builder()
                    .amountGb(Math.random())
                    .occurredAt(LocalDate.now())
                    .subscriptionEntity(entry)
                    .build();

            usageRecordRepository.save(usageRecordEntity);
        }

    }
}
