package com.proiect.subtrack.repositories;

import com.proiect.subtrack.domain.entities.PlanEntity;
import com.proiect.subtrack.domain.entities.UsageRecordEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends CrudRepository<PlanEntity, Long> ,
        PagingAndSortingRepository<PlanEntity, Long> {
}
