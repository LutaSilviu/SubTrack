package com.proiect.subtrack.services;
import com.proiect.subtrack.domain.entities.PlanEntity;

import java.util.List;
import java.util.Optional;


public interface PlanService {

    PlanEntity save(PlanEntity planEntity);

    List<PlanEntity> findAll();

    List<PlanEntity> findAllActive();

    PlanEntity toggleActiveStatus(Long id);
}
