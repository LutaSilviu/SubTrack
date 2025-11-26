package com.proiect.subtrack.services.impl;

import com.proiect.subtrack.domain.entities.UserEntity;
import com.proiect.subtrack.repositories.UserRepository;
import com.proiect.subtrack.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<UserEntity> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<UserEntity> getUserById(Long id) {
        return  userRepository.findById(id);
    }

    @Override
    public boolean exists(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public UserEntity partialUpdate(Long id, UserEntity userEntity) {
       Optional<UserEntity> existingEntity = userRepository.findById(id);

       return existingEntity.map(
               entity -> {
                   Optional.ofNullable(userEntity.getName()).ifPresent(entity :: setName);
                   Optional.ofNullable(userEntity.getAddress()).ifPresent(entity :: setAddress);
                   Optional.ofNullable(userEntity.getDateOfBirth()).ifPresent(entity :: setDateOfBirth);
                   return save(entity);
               }
       ).orElseThrow(() -> new RuntimeException("No existing user found"));
    }

    @Override
    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
