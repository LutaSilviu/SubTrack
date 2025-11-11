package com.proiect.subtrack.repositories;

import com.proiect.subtrack.domain.dto.SubscriptionUserViewDto;
import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import com.proiect.subtrack.domain.entities.UsageRecordEntity;
import com.proiect.subtrack.utils.SubscriptionStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity , Long>,
        PagingAndSortingRepository<SubscriptionEntity, Long> {

    @Query("""
    select new com.proiect.subtrack.domain.dto.SubscriptionUserViewDto(
        u.name, u.email, u.dateOfBirth,
        p.name, p.price, p.includedGb, p.overagePrice,
        s.status, s.createdAt, s.phoneNumber,
        s.currentCycleStart, s.currentCycleStop
    )
    from SubscriptionEntity s
    join s.userEntity u
    join s.planEntity p
   
    """)
    List<SubscriptionUserViewDto> findCurrentSubscriptions(
            @Param("today") LocalDate today,
            @Param("status") SubscriptionStatus status
    );


    List<SubscriptionEntity> getSubscriptionEntitiesByCurrentCycleStopEquals(LocalDate currentCycleStop);

    Iterable<SubscriptionEntity> findByStatus(SubscriptionStatus status);
}
