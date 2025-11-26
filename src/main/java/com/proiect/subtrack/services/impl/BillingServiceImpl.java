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
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Transactional
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    final private Integer CYCLE_DURATION_DAYS = 30;

    final private SubscriptionRepository subscriptionRepository;
    final private UsageRecordRepository usageRecordRepository;

    final private InvoiceRepository invoiceRepository;

    @Override
    public void calculateInvoicePerSubscription(SubscriptionEntity subscriptionEntity) {
        //get subscription for which we calculate total consumption

        //get all the reports for consumption by the subscription, for the current time interval
        Optional<List<UsageRecordEntity>> usageRecordEntityList = usageRecordRepository.getUsageRecordEntitiesBySubscriptionEntityAndOccurredAtIsBetween(subscriptionEntity,subscriptionEntity.getCurrentCycleStart(),subscriptionEntity.getCurrentCycleStop());

        //variable used to calculate total consumption
        Double gbConsumed = 0D;


        if(usageRecordEntityList.isPresent())
        {
            for( UsageRecordEntity entity : usageRecordEntityList.get()){
                //add all the consumption from the list of usageEntityes
                gbConsumed+=entity.getAmountGb();
            }
        }


        PlanEntity planEntity = subscriptionEntity.getPlan();

        //the gb of the plan
        Integer includedGb = planEntity.getIncludedGb();

        //what is over the limit( can be 0 or negative)
        double extraGbConsumed = gbConsumed - includedGb;

        //the standard price
        Double price = planEntity.getPrice();



        Double overageCost = 0D;
        if(extraGbConsumed > 0){
            //calculate  the overconsumption
            overageCost+= extraGbConsumed*planEntity.getOveragePrice();
        }


        //create the invoice
        InvoiceEntity invoiceEntity = InvoiceEntity.builder()
                .subscriptionEntity(subscriptionEntity)
                .basePrice(price)
                .overageCost(overageCost)
                .total(price+overageCost)
                .periodStart(subscriptionEntity.getCurrentCycleStart())
                .periodStop(subscriptionEntity.getCurrentCycleStop())
                .status(InvoiceStatus.ISSUED)
                .createdAt(LocalDate.now())
                .build();


        //save the invoice
        invoiceRepository.save(invoiceEntity);

        //modify the cycle
        subscriptionEntity.setCurrentCycleStart(subscriptionEntity.getCurrentCycleStop());
        subscriptionEntity.setCurrentCycleStop(subscriptionEntity.getCurrentCycleStop().plusDays(CYCLE_DURATION_DAYS));

        //update the subscription
        subscriptionRepository.save(subscriptionEntity);
    }

    @Override
    @Scheduled(cron = "0 5 0 * * *", zone = "Europe/Bucharest")
    public void runDailyBilling() {

        List<SubscriptionEntity> subscriptionEntitiesByCurrentCycleStopEquals = subscriptionRepository.getSubscriptionEntitiesByCurrentCycleStopEquals(LocalDate.now());

        for(SubscriptionEntity subscriptionEntity : subscriptionEntitiesByCurrentCycleStopEquals){
            calculateInvoicePerSubscription(subscriptionEntity);
        }
    }
}
