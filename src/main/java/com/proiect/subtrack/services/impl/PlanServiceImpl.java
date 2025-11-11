package com.proiect.subtrack.services.impl;

import com.proiect.subtrack.domain.entities.PlanEntity;
import com.proiect.subtrack.domain.entities.UserEntity;
import com.proiect.subtrack.repositories.PlanRepository;
import com.proiect.subtrack.services.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;

    @Override
    public PlanEntity save(PlanEntity planEntity) {
        return planRepository.save(planEntity);
    }

    @Override
    public Page<PlanEntity> findAll(Pageable pageable) {
        return  planRepository.findAll(pageable);
    }

    @Override
    public List<PlanEntity> findAll() {
        return StreamSupport.stream(planRepository.findAll().spliterator(),false).toList();
    }

}
