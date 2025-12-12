package com.proiect.subtrack.services.impl;

import com.proiect.subtrack.domain.entities.InvoiceEntity;
import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import com.proiect.subtrack.domain.entities.UsageRecordEntity;
import com.proiect.subtrack.repositories.InvoiceRepository;
import com.proiect.subtrack.services.InvoiceService;
import com.proiect.subtrack.services.SubscriptionService;
import com.proiect.subtrack.services.UsageRecordService;
import com.proiect.subtrack.utils.InvoiceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final SubscriptionService subscriptionService;
    private final UsageRecordService usageRecordService;

    @Override
    @Transactional
    @Cacheable(value = "subscriptionInvoices", key = "#subscriptionId")
    public List<InvoiceEntity> getInvoiceForSubscription(Long subscriptionId) {
        log.debug("Fetching invoices for subscription ID: {}", subscriptionId);
        SubscriptionEntity subscriptionEntity = subscriptionService.getSubscriptionById(subscriptionId);
        List<InvoiceEntity> invoices = invoiceRepository.findAllBySubscription(subscriptionEntity);
        log.info("Retrieved {} invoices for subscription ID: {}", invoices.size(), subscriptionId);
        return invoices;
    }

    @Override
    @Transactional
    @CacheEvict(value = "subscriptionInvoices", key = "#subscriptionId")
    public InvoiceEntity save(Long subscriptionId) {
        log.info("Creating invoice for subscription ID: {}", subscriptionId);

        List<UsageRecordEntity> usageRecordsForSubscriptionInRange = usageRecordService.getUsageRecordsForSubscriptionInRange(subscriptionId);

        SubscriptionEntity subscriptionEntity = subscriptionService.getSubscriptionById(subscriptionId);

        AtomicReference<Double> totalConsumtion = new AtomicReference<>(0.0);

        usageRecordsForSubscriptionInRange.forEach(
                usageRecordEntity ->
                    totalConsumtion.updateAndGet(v -> v + usageRecordEntity.getAmountGb())
        );

        log.debug("Total consumption for subscription ID {}: {} GB", subscriptionId, totalConsumtion.get());

        Double overageConsumption = totalConsumtion.get() - subscriptionEntity.getPlan().getIncludedGb();

        Double totalCost = subscriptionEntity.getPlan().getPrice();

        if(overageConsumption > 0){
            totalCost+= overageConsumption* subscriptionEntity.getPlan().getOveragePrice();
            log.debug("Overage detected for subscription ID {}: {} GB, extra cost: {}",
                subscriptionId, overageConsumption, overageConsumption * subscriptionEntity.getPlan().getOveragePrice());
        }

        InvoiceEntity invoiceEntity = InvoiceEntity
                .builder()
                .total(totalCost)
                .status(InvoiceStatus.DRAFTED)
                .overageCost(overageConsumption > 0 ?
                        overageConsumption* subscriptionEntity.getPlan().getOveragePrice() :
                        0
                        )
                .periodStart(subscriptionEntity.getCurrentCycleStart())
                .periodStop(subscriptionEntity.getCurrentCycleStop())
                .createdAt(LocalDate.now())
                .basePrice(subscriptionEntity.getPlan().getPrice())
                .build();

        subscriptionEntity.setCurrentCycleStart(LocalDate.now());
        subscriptionEntity.setCurrentCycleStop(LocalDate.now().plusDays(30));

        invoiceEntity.setSubscription(subscriptionService.updateSubscription(subscriptionEntity));

        InvoiceEntity saved = invoiceRepository.save(invoiceEntity);
        log.info("Invoice created successfully for subscription ID: {}, total cost: {}", subscriptionId, totalCost);
        return saved;
    }
}
