package com.proiect.subtrack.services;

import com.proiect.subtrack.domain.dto.SubscriptionUserViewDto;
import com.proiect.subtrack.domain.entities.PlanEntity;
import com.proiect.subtrack.domain.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PlanService {

    PlanEntity save(PlanEntity planEntity);

    Page<PlanEntity> findAll(Pageable pageable);

    List<PlanEntity> findAll();
}
