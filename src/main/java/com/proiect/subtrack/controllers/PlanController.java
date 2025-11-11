package com.proiect.subtrack.controllers;


import com.proiect.subtrack.domain.dto.PlanDto;
import com.proiect.subtrack.domain.dto.SubscriptionDto;
import com.proiect.subtrack.domain.entities.PlanEntity;
import com.proiect.subtrack.mappers.impl.PlanMapperImpl;
import com.proiect.subtrack.services.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/plans")
public class PlanController {

    final private PlanService planService;
    final private PlanMapperImpl planMapper;

    @GetMapping
    ResponseEntity<List<PlanDto>>  getAllPlans(){
        List<PlanDto> all = planService.findAll().stream().map(planMapper::mapTo).toList();

        return ResponseEntity.ok(all);
    }

}
