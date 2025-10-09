package com.proiect.subtrack.repositories;

import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import com.proiect.subtrack.domain.entities.UsageRecordEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity , Long>,
        PagingAndSortingRepository<SubscriptionEntity, Long> {
}
