package com.proiect.subtrack.services.impl;

import com.proiect.subtrack.domain.dto.SubscriptionUserViewDto;
import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import com.proiect.subtrack.domain.entities.UserEntity;
import com.proiect.subtrack.repositories.SubscriptionRepository;
import com.proiect.subtrack.services.SubscriptionService;
import com.proiect.subtrack.services.UserService;
import com.proiect.subtrack.utils.SubscriptionStatus;
import com.proiect.subtrack.utils.errors.SubscriptionNotFoundError;
import com.proiect.subtrack.utils.errors.UserAlreadyExistsException;
import com.proiect.subtrack.utils.validation.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {

    final private SubscriptionRepository subscriptionRepository;
    final private UserService userService;
    final private ValidationUtils validationUtils;

    @Override
    @Transactional
    @CachePut(value = "subscriptions", key = "#subscriptionEntity.subscriptionId")
    @CacheEvict(value = "userSubscriptions", allEntries = true)
    public SubscriptionEntity save(SubscriptionEntity subscriptionEntity) {
        log.info("Attempting to save subscription for phone number: {}", subscriptionEntity.getPhoneNumber());

        // Validate phone number
        validationUtils.validatePhoneNumber(subscriptionEntity.getPhoneNumber());

        UserEntity entityUser = subscriptionEntity.getUser();

        // Validate user data
        validationUtils.validateName(entityUser.getName());
        validationUtils.validateEmail(entityUser.getEmail());
        validationUtils.validateAddress(entityUser.getAddress());

        if(!subscriptionRepository.getSubscriptionEntitiesByPhoneNumber(subscriptionEntity.getPhoneNumber()).isEmpty())
        {
            log.warn("Subscription already exists for phone number: {}", subscriptionEntity.getPhoneNumber());
            throw new UserAlreadyExistsException("User with this number exists already");
        }

        entityUser.setUserId(null);
        log.debug("Saving user for subscription: {}", entityUser.getEmail());
        UserEntity savedUserEntity = userService.save(entityUser);

        subscriptionEntity.setUser(savedUserEntity);

        SubscriptionEntity saved = subscriptionRepository.save(subscriptionEntity);
        log.info("Subscription saved successfully with ID: {}", saved.getSubscriptionId());
        return saved;
    }

    @Override
    @Cacheable(value = "currentSubscriptions", key = "'current'")
    public List<SubscriptionUserViewDto> getCurrent() {
        log.debug("Fetching current active subscriptions");
        List<SubscriptionUserViewDto> subscriptions = subscriptionRepository.findCurrentSubscriptions(LocalDate.now(), SubscriptionStatus.ACTIVE);
        log.info("Retrieved {} current subscriptions", subscriptions.size());
        return subscriptions;
    }

    @Override
    @Cacheable(value = "todaySubscriptions", key = "'today'")
    public List<SubscriptionUserViewDto> getAllForToday() {
        log.debug("Fetching all subscriptions for today");
        List<SubscriptionUserViewDto> subscriptions = subscriptionRepository.findCurrentSubscriptions(LocalDate.now(), null);
        log.info("Retrieved {} subscriptions for today", subscriptions.size());
        return subscriptions;
    }

    @Override
    @CacheEvict(value = {"subscriptions", "userSubscriptions"}, allEntries = true)
    public void updateStatus(Long id, SubscriptionStatus status) {
        log.info("Updating status for subscription ID {} to {}", id, status);
        subscriptionRepository.updateStatus(id, status);
        log.debug("Status updated successfully for subscription ID: {}", id);
    }

    @Override
    @Cacheable(value = "userSubscriptions", key = "#userId")
    public List<SubscriptionEntity> getSubscriptionsForUser(Long userId) {
        log.debug("Fetching subscriptions for user ID: {}", userId);
        List<SubscriptionEntity> subscriptions = subscriptionRepository.findAllByUser_UserId(userId);
        log.info("Retrieved {} subscriptions for user ID: {}", subscriptions.size(), userId);
        return subscriptions;

    }

    @Override
    @Transactional
    @Cacheable(value = "subscriptions", key = "#subscriptionId", unless = "#result == null")
    public SubscriptionEntity getSubscriptionById(Long subscriptionId) {
        log.debug("Fetching subscription by ID: {}", subscriptionId);
        Optional<SubscriptionEntity> byId = subscriptionRepository.findById(subscriptionId);
        if(byId.isPresent()) {
            log.debug("Subscription found with ID: {}", subscriptionId);
            return byId.get();
        } else {
            log.error("Subscription not found with ID: {}", subscriptionId);
            throw new SubscriptionNotFoundError("Subscription with id:"+subscriptionId+" was not found");
        }
    }

    @Override
    @CachePut(value = "subscriptions", key = "#subscriptionEntity.subscriptionId")
    @CacheEvict(value = "userSubscriptions", allEntries = true)
    public SubscriptionEntity updateSubscription(SubscriptionEntity subscriptionEntity) {
        log.info("Updating subscription with ID: {}", subscriptionEntity.getSubscriptionId());
        if(!subscriptionRepository.existsById(subscriptionEntity.getSubscriptionId())) {
            log.error("Cannot update - subscription not found with ID: {}", subscriptionEntity.getSubscriptionId());
            throw new SubscriptionNotFoundError("Subscription with id="
                    + subscriptionEntity.getSubscriptionId() +
                    " was not found");
        }
        SubscriptionEntity updated = subscriptionRepository.save(subscriptionEntity);
        log.info("Subscription updated successfully with ID: {}", updated.getSubscriptionId());
        return updated;
    }
}
