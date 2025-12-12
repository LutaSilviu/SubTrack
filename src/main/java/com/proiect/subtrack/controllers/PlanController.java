package com.proiect.subtrack.controllers;


import com.proiect.subtrack.domain.dto.PlanDto;
import com.proiect.subtrack.mappers.impl.PlanMapperImpl;
import com.proiect.subtrack.services.PlanService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/plans")
@Tag(name = "Plans", description = "Plan management endpoints")
public class PlanController {

    final private PlanService planService;
    final private PlanMapperImpl planMapper;

    @GetMapping
    @Operation(summary = "Get all plans", description = "Returns a list of all plans")
    public ResponseEntity<@NonNull List<PlanDto>>  getAllPlans(){
        log.debug("Fetching all plans");
        List<PlanDto> all = planService.findAll().stream().map(planMapper::mapTo).toList();
        log.info("Retrieved {} plans", all.size());
        return ResponseEntity.ok(all);
    }

}
