package com.proiect.subtrack.repositories;

import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import com.proiect.subtrack.domain.entities.UsageRecordEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UsageRecordRepository extends CrudRepository<UsageRecordEntity, Long>,
        PagingAndSortingRepository<UsageRecordEntity, Long> {

  List<UsageRecordEntity> getUsageRecordEntitiesBySubscriptionAndOccurredAtIsBetween(SubscriptionEntity subscriptionEntity, LocalDate occurredAtAfter, LocalDate occurredAtBefore);

}
