package com.proiect.subtrack.services.impl;

import com.proiect.subtrack.domain.entities.InvoiceEntity;
import com.proiect.subtrack.domain.entities.PlanEntity;
import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import com.proiect.subtrack.domain.entities.UsageRecordEntity;
import com.proiect.subtrack.repositories.InvoiceRepository;
import com.proiect.subtrack.repositories.SubscriptionRepository;
import com.proiect.subtrack.repositories.UsageRecordRepository;
import com.proiect.subtrack.services.BillingService;
import com.proiect.subtrack.utils.InvoiceStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BillingServiceImpl implements BillingService {

    final private Integer CYCLE_DURATION_DAYS = 30;

    final private SubscriptionRepository subscriptionRepository;
    final private UsageRecordRepository usageRecordRepository;

    final private InvoiceRepository invoiceRepository;

    @Override
    @CacheEvict(value = "subscriptionInvoices", key = "#subscriptionEntity.subscriptionId")
    public void calculateInvoicePerSubscription(SubscriptionEntity subscriptionEntity) {
        log.info("Calculating invoice for subscription ID: {}", subscriptionEntity.getSubscriptionId());

        List<UsageRecordEntity> usageRecordEntityList = usageRecordRepository.getUsageRecordEntitiesBySubscriptionAndOccurredAtIsBetween(subscriptionEntity,subscriptionEntity.getCurrentCycleStart(),subscriptionEntity.getCurrentCycleStop());

        Double gbConsumed = 0D;

        for( UsageRecordEntity entity : usageRecordEntityList){
            gbConsumed+=entity.getAmountGb();
        }

        log.debug("Total GB consumed for subscription ID {}: {}", subscriptionEntity.getSubscriptionId(), gbConsumed);

        PlanEntity planEntity = subscriptionEntity.getPlan();

        Integer includedGb = planEntity.getIncludedGb();

        double extraGbConsumed = gbConsumed - includedGb;

        Double price = planEntity.getPrice();

        Double overageCost = 0D;
        if(extraGbConsumed > 0){
            overageCost+= extraGbConsumed*planEntity.getOveragePrice();
            log.debug("Overage cost for subscription ID {}: {} (extra GB: {})",
                subscriptionEntity.getSubscriptionId(), overageCost, extraGbConsumed);
        }

        InvoiceEntity invoiceEntity = InvoiceEntity.builder()
                .subscription(subscriptionEntity)
                .basePrice(price)
                .overageCost(overageCost)
                .total(price+overageCost)
                .periodStart(subscriptionEntity.getCurrentCycleStart())
                .periodStop(subscriptionEntity.getCurrentCycleStop())
                .status(InvoiceStatus.ISSUED)
                .createdAt(LocalDate.now())
                .build();

        invoiceRepository.save(invoiceEntity);

        subscriptionEntity.setCurrentCycleStart(subscriptionEntity.getCurrentCycleStop());
        subscriptionEntity.setCurrentCycleStop(subscriptionEntity.getCurrentCycleStop().plusDays(CYCLE_DURATION_DAYS));

        subscriptionRepository.save(subscriptionEntity);
        log.info("Invoice calculated and saved for subscription ID: {}, total: {}",
            subscriptionEntity.getSubscriptionId(), price + overageCost);
    }

    @Override
    @Scheduled(cron = "0 5 0 * * *", zone = "Europe/Bucharest")
    public void runDailyBilling() {
        log.info("Starting daily billing process");

        List<SubscriptionEntity> subscriptionEntitiesByCurrentCycleStopEquals = subscriptionRepository.getSubscriptionEntitiesByCurrentCycleStopEquals(LocalDate.now());

        log.info("Found {} subscriptions to bill", subscriptionEntitiesByCurrentCycleStopEquals.size());

        for(SubscriptionEntity subscriptionEntity : subscriptionEntitiesByCurrentCycleStopEquals){
            try {
                calculateInvoicePerSubscription(subscriptionEntity);
            } catch (Exception e) {
                log.error("Error calculating invoice for subscription ID: {}",
                    subscriptionEntity.getSubscriptionId(), e);
            }
        }

        log.info("Daily billing process completed");
    }
}
