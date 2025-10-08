package com.proiect.subtrack.repositories;

import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity , Long> {
}
