package com.proiect.subtrack.controllers;


import com.proiect.subtrack.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
public class SubscriptionViewController {

    private final SubscriptionService service;


    @GetMapping("/view")
    public String listCurrent(Model model) {
        model.addAttribute("rows", service.getCurrent());
        return "subscriptions/table"; // trimite spre template-ul Thymeleaf
    }
}
