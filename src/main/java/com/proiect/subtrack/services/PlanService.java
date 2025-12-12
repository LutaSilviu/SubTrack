package com.proiect.subtrack.services;
import com.proiect.subtrack.domain.entities.PlanEntity;

import java.util.List;


public interface PlanService {

    PlanEntity save(PlanEntity planEntity);

    List<PlanEntity> findAll();
}
