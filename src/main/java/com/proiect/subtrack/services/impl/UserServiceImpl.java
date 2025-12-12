package com.proiect.subtrack.services.impl;

import com.proiect.subtrack.domain.entities.UserEntity;
import com.proiect.subtrack.repositories.UserRepository;
import com.proiect.subtrack.services.UserService;
import com.proiect.subtrack.utils.validation.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ValidationUtils validationUtils;


    @Override
    public List<UserEntity> getAll() {
        log.debug("Fetching all users");
        List<UserEntity> users = StreamSupport.stream(userRepository.findAll().spliterator() , false).toList();
        log.info("Retrieved {} users", users);
        return users;
    }

    @Override
    @CachePut(value = "users", key = "#userEntity.userId")
    public UserEntity save(UserEntity userEntity) {
        log.info("Saving user: {}", userEntity.getEmail());

        // Validate user data
        validationUtils.validateName(userEntity.getName());
        validationUtils.validateEmail(userEntity.getEmail());
        validationUtils.validateAddress(userEntity.getAddress());

        UserEntity saved = userRepository.save(userEntity);
        log.debug("User saved successfully with ID: {}", saved.getUserId());
        return saved;
    }

    @Override
    @CacheEvict(value = "users", key = "#id")
    public void delete(Long id) {
        log.info("Deleting user with ID: {}", id);
        userRepository.deleteById(id);
        log.debug("User deleted successfully with ID: {}", id);
    }

    @Override
    @Cacheable(value = "users", key = "#id", unless = "#result == null or !#result.isPresent()")
    public Optional<UserEntity> getUserById(Long id) {
        log.debug("Fetching user by ID: {}", id);
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            log.debug("User found with ID: {}", id);
        } else {
            log.warn("No user found with ID: {}", id);
        }
        return user;
    }

    @Override
    public boolean exists(Long id) {
        log.debug("Checking if user exists with ID: {}", id);
        boolean exists = userRepository.existsById(id);
        log.debug("User exists check for ID {}: {}", id, exists);
        return exists;
    }

    @Override
    @CachePut(value = "users", key = "#id")
    public UserEntity partialUpdate(Long id, UserEntity userEntity) {
        log.info("Partially updating user with ID: {}", id);
        Optional<UserEntity> existingEntity = userRepository.findById(id);

        return existingEntity.map(
                entity -> {
                    Optional.ofNullable(userEntity.getName()).ifPresent(name -> {
                        log.debug("Updating name for user ID {}", id);
                        entity.setName(name);
                    });
                    Optional.ofNullable(userEntity.getAddress()).ifPresent(address -> {
                        log.debug("Updating address for user ID {}", id);
                        entity.setAddress(address);
                    });
                    Optional.ofNullable(userEntity.getDateOfBirth()).ifPresent(dob -> {
                        log.debug("Updating date of birth for user ID {}", id);
                        entity.setDateOfBirth(dob);
                    });
                    log.info("User partially updated successfully with ID: {}", id);
                    return save(entity);
                }
        ).orElseThrow(() -> {
            log.error("No existing user found with ID: {}", id);
            return new RuntimeException("No existing user found");
        });
    }

    @Override
    @Cacheable(value = "usersByEmail", key = "#email", unless = "#result == null or !#result.isPresent()")
    public Optional<UserEntity> getUserByEmail(String email) {
        log.debug("Fetching user by email: {}", email);
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            log.debug("User found with email: {}", email);
        } else {
            log.warn("No user found with email: {}", email);
        }
        return user;
    }


}
