package com.proiect.subtrack.repositories;

import com.proiect.subtrack.domain.entities.InvoiceEntity;
import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
    List<InvoiceEntity> findAllBySubscription(SubscriptionEntity subscriptionEntity);
}
