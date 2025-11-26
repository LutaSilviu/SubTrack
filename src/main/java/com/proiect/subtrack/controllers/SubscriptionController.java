package com.proiect.subtrack.controllers;

import com.proiect.subtrack.domain.dto.SubscriptionDto;
import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import com.proiect.subtrack.mappers.impl.SubscriptionMapperImpl;
import com.proiect.subtrack.services.SubscriptionService;
import com.proiect.subtrack.utils.errors.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
@Tag(name = "Subscriptions", description = "Subscription management endpoints")
public class SubscriptionController {

    final private SubscriptionService subscriptionService;
    final private SubscriptionMapperImpl subscriptionMapper;

    @PostMapping("/create")
    @Operation(summary = "Create a subscription", description = "Create a new subscription from a DTO")
    public ResponseEntity<SubscriptionDto> createSubscription(@RequestBody SubscriptionDto subscriptionDto){

        SubscriptionEntity subscriptionEntity = subscriptionMapper.mapFrom(subscriptionDto);

        // Ensure ID is null for new entities
        subscriptionEntity.setSubscriptionId(null);


        SubscriptionEntity savedEntity = subscriptionService.save(subscriptionEntity);


        return new ResponseEntity<>(subscriptionMapper.mapTo(savedEntity),
                HttpStatus.CREATED);

    }


}
