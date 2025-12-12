package com.proiect.subtrack.repositories;

import com.proiect.subtrack.domain.entities.PlanEntity;
import com.proiect.subtrack.domain.entities.UsageRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<PlanEntity, Long> {
}
