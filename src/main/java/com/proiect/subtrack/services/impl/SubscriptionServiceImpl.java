package com.proiect.subtrack.services.impl;

import com.proiect.subtrack.domain.dto.SubscriptionUserViewDto;
import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import com.proiect.subtrack.domain.entities.UserEntity;
import com.proiect.subtrack.repositories.SubscriptionRepository;
import com.proiect.subtrack.services.SubscriptionService;
import com.proiect.subtrack.services.UserService;
import com.proiect.subtrack.utils.SubscriptionStatus;
import com.proiect.subtrack.utils.errors.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    final private SubscriptionRepository subscriptionRepository;
    final private UserService userService;

    @Override
    @Transactional
    public SubscriptionEntity save(SubscriptionEntity subscriptionEntity) {

        UserEntity entityUser = subscriptionEntity.getUser();

        if(userService.getUserByEmail(entityUser.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("User with this email exists already");
        }
        entityUser.setUserId(null);
        UserEntity savedUserEntity = userService.save(entityUser);

        subscriptionEntity.setUser(savedUserEntity);

        return subscriptionRepository.save(subscriptionEntity);
    }

    public List<SubscriptionUserViewDto> getCurrent() {
        return subscriptionRepository.findCurrentSubscriptions(LocalDate.now(), SubscriptionStatus.ACTIVE);
    }

    public List<SubscriptionUserViewDto> getAllForToday() {
        return subscriptionRepository.findCurrentSubscriptions(LocalDate.now(), null);
    }

    @Override
    public void updateStatus(Long id, SubscriptionStatus status) {
        subscriptionRepository.updateStatus(id, status);
    }
}
