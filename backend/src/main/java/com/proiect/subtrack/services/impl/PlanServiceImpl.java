package com.proiect.subtrack.services.impl;

import com.proiect.subtrack.domain.entities.PlanEntity;
import com.proiect.subtrack.repositories.PlanRepository;
import com.proiect.subtrack.services.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;

    @Override
    @CachePut(value = "plans", key = "#planEntity.planId")
    @CacheEvict(value = "allPlans", allEntries = true)
    public PlanEntity save(PlanEntity planEntity) {
        log.info("Saving plan: {}", planEntity.getName());

        PlanEntity saved = planRepository.save(planEntity);
        log.debug("Plan saved successfully with ID: {}", saved.getPlanId());
        return saved;
    }

    @Override
    @Cacheable(value = "allActivePlans", key = "'allActive'")
    public List<PlanEntity> findAllActive() {
        log.debug("Fetching all Active plans");
        List<PlanEntity> plans = planRepository.findAllByActiveTrue();
        log.info("Retrieved {} plans", plans.size());
        return plans;
    }

    @Override
    @Cacheable(value = "allPlans", key = "'all'")
    public List<PlanEntity> findAll() {
        log.debug("Fetching all plans");
        List<PlanEntity> plans = planRepository.findAll();
        log.info("Retrieved {} active plans", plans.size());
        return plans;
    }

    @Override
    @CachePut(value = "plans", key = "#id")
    @CacheEvict(value = {"allPlans", "allActivePlans"}, allEntries = true)
    public PlanEntity toggleActiveStatus(Long id) {
        log.info("Toggling active status for plan ID: {}", id);
        PlanEntity plan = planRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Plan not found with id: " + id));

        plan.setActive(!plan.isActive());
        PlanEntity updated = planRepository.save(plan);
        log.info("Plan ID {} status changed to: {}", id, updated.isActive() ? "ACTIVE" : "INACTIVE");
        return updated;
    }

}
