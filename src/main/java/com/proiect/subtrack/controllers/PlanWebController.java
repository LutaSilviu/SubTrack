package com.proiect.subtrack.controllers;

import com.proiect.subtrack.domain.dto.PlanDto;
import com.proiect.subtrack.domain.entities.PlanEntity;
import com.proiect.subtrack.mappers.impl.PlanMapperImpl;
import com.proiect.subtrack.services.PlanService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/plans")
public class PlanWebController {

    final private PlanService planService;
    final private PlanMapperImpl planMapper;

    @GetMapping("/view")
    public String index(Model model) {
        model.addAttribute("plans", planService.findAll());
        return "plans/index";
    }



    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("plan", new PlanDto());
        return "plans/create";
    }

    @PostMapping("/create")
    public String createSubmit(@ModelAttribute PlanDto planDto) {
        PlanEntity plan = planMapper.mapFrom(planDto);
        planService.save(plan);
        return "redirect:/plans/view";
    }


}


