package com.proiect.subtrack.repositories;

import com.proiect.subtrack.domain.entities.UsageRecordEntity;
import com.proiect.subtrack.domain.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsageRecordRepository extends CrudRepository<UsageRecordEntity, Long>,
        PagingAndSortingRepository<UsageRecordEntity, Long> {
}
