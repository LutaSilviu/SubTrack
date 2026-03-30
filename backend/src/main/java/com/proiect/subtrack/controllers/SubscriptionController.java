package com.proiect.subtrack.controllers;

import com.proiect.subtrack.domain.dto.SubscriptionDto;
import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import com.proiect.subtrack.mappers.impl.SubscriptionMapperImpl;
import com.proiect.subtrack.services.SubscriptionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
@Tag(name = "Subscriptions", description = "Subscription management endpoints")
public class SubscriptionController {

    final private SubscriptionService subscriptionService;
    final private SubscriptionMapperImpl subscriptionMapper;

    @PostMapping("/create")
    @Operation(summary = "Create a subscription", description = "Create a new subscription from a DTO")
    public ResponseEntity<@NonNull SubscriptionDto> createSubscription(@RequestBody SubscriptionDto subscriptionDto){
        log.info("Creating subscription for phone number: {}", subscriptionDto.getPhoneNumber());

        SubscriptionEntity subscriptionEntity = subscriptionMapper.mapFrom(subscriptionDto);

        // Ensure ID is null for new entities
        subscriptionEntity.setSubscriptionId(null);

        SubscriptionEntity savedEntity = subscriptionService.save(subscriptionEntity);

        log.info("Subscription created successfully with ID: {}", savedEntity.getSubscriptionId());
        return new ResponseEntity<>(subscriptionMapper.mapTo(savedEntity),
                HttpStatus.CREATED);

    }


    @GetMapping("/user_subscriptions/{id}")
    @Operation(summary = "Get subscriptions for user", description = "Get all the subscriptions associated with the user")
    public ResponseEntity<List<SubscriptionDto>> getListOfSubscriptionForUser(@PathVariable Long id){
        log.debug("Fetching subscriptions for user ID: {}", id);

        List<SubscriptionEntity> listOfSubscriptions = subscriptionService.getSubscriptionsForUser(id);

        List<SubscriptionDto> listOfDtos = listOfSubscriptions.stream().map(
                subscriptionMapper::mapTo
        ).toList();

        log.info("Retrieved {} subscriptions for user ID: {}", listOfDtos.size(), id);
        return ResponseEntity.ok(listOfDtos);
    }
}
