package com.proiect.subtrack.repositories;

import com.proiect.subtrack.domain.dto.SubscriptionUserViewDto;
import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import com.proiect.subtrack.domain.entities.UserEntity;
import com.proiect.subtrack.utils.SubscriptionStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

    @Query("""
    select new com.proiect.subtrack.domain.dto.SubscriptionUserViewDto(
        s.subscriptionId,
        u.name, u.email, u.dateOfBirth,
        p.name, p.price, p.includedGb, p.overagePrice,
        s.status, s.createdAt, s.phoneNumber,
        s.currentCycleStart, s.currentCycleStop
    )
    from SubscriptionEntity s
    join s.user u
    join s.plan p
   
    """)
    List<SubscriptionUserViewDto> findCurrentSubscriptions(
            @Param("today") LocalDate today,
            @Param("status") SubscriptionStatus status
    );


    List<SubscriptionEntity> getSubscriptionEntitiesByCurrentCycleStopEquals(LocalDate currentCycleStop);

    Iterable<SubscriptionEntity> findByStatus(SubscriptionStatus status);

    @Query("UPDATE SubscriptionEntity s SET s.status = :status WHERE s.subscriptionId = :id")
    @Modifying
    @Transactional
    void updateStatus(@Param("id") Long id, @Param("status") SubscriptionStatus status);

    List<SubscriptionEntity> findAllByUser_UserId(Long userUserId);

    Long user(UserEntity user);

    List<SubscriptionEntity> getSubscriptionEntitiesByPhoneNumber(String phoneNumber);
}
