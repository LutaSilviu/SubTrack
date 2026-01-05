package com.proiect.subtrack.services.impl;

import com.proiect.subtrack.domain.dto.UserDto;
import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import com.proiect.subtrack.domain.entities.UserEntity;
import com.proiect.subtrack.mappers.impl.UserMapperImpl;
import com.proiect.subtrack.repositories.UserRepository;
import com.proiect.subtrack.services.AuthService;
import com.proiect.subtrack.services.SubscriptionService;
import com.proiect.subtrack.utils.SubscriptionStatus;
import com.proiect.subtrack.utils.errors.NoActiveSubscriptionsException;
import com.proiect.subtrack.utils.errors.NoUserFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final SubscriptionService subscriptionService;
    private final UserMapperImpl userMapperImpl;

    @Override
    @Cacheable(value = "userLogins", key = "#email", unless = "#result == null")
    public UserDto login(String email) {
        {
            log.info("Login attempt for email: {}", email);
            
            Optional<UserEntity> userEntity = userRepository.findByEmail(email);

            if(userEntity.isPresent()) {
                log.debug("User found for email: {}, checking subscriptions", email);
                List<SubscriptionEntity> subscriptionsForUser = subscriptionService
                        .getSubscriptionsForUser(userEntity.get().getUserId());

                for(var subscriptionEntity : subscriptionsForUser ) {
                    if (subscriptionEntity.getStatus().equals(SubscriptionStatus.ACTIVE)) {
                        log.info("Login successful for email: {} with active subscription", email);

                        return userMapperImpl.mapTo(userEntity.get());
                    }
                }
                log.warn("No active subscriptions found for user with email: {}", email);
                throw new NoActiveSubscriptionsException("No active subscriptions found for user");
            }
            log.warn("No user found for email: {}", email);
            throw new NoUserFoundException("Credențiale invalide. Utilizatorul nu există.");
        }
    }
}

