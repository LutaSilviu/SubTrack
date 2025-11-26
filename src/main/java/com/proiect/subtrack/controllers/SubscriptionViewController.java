package com.proiect.subtrack.controllers;


import com.proiect.subtrack.services.SubscriptionService;
import com.proiect.subtrack.utils.SubscriptionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
@Tag(name = "Subscriptions UI", description = "Thymeleaf views for subscriptions")
public class SubscriptionViewController {

    private final SubscriptionService service;


    @GetMapping("/view")
    @Operation(summary = "View current subscriptions", description = "Returns the subscriptions table view")
    public String listCurrent(Model model) {
        model.addAttribute("rows", service.getCurrent());
        return "subscriptions/table";
    }


    @PostMapping("/update-status")
    @Operation(summary = "Update subscription status", description = "Update the status of a subscription by id")
    public String updateStatus(@RequestParam Long id, @RequestParam SubscriptionStatus status){
        service.updateStatus(id, status);
        return "redirect:/subscriptions/view";
    }
}
