package com.proiect.subtrack.controllers;

import com.proiect.subtrack.domain.dto.SubscriptionDto;
import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import com.proiect.subtrack.mappers.impl.SubscriptionMapperImpl;
import com.proiect.subtrack.services.SubscriptionService;
import com.proiect.subtrack.services.impl.SubscriptionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
public class SubscriptionController {

    final private SubscriptionService subscriptionService;
    final private SubscriptionMapperImpl subscriptionMapper;
    @PostMapping("/create")
    ResponseEntity<SubscriptionDto> createSubscription(SubscriptionDto subscriptionDto){

        SubscriptionEntity subscriptionEntity = subscriptionMapper.mapFrom(subscriptionDto);

        SubscriptionEntity savedEntity = subscriptionService.save(subscriptionEntity);

        return new ResponseEntity<>(subscriptionMapper.mapTo(savedEntity),
                HttpStatus.CREATED);
    }


}
