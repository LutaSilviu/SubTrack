package com.proiect.subtrack.services;

import com.proiect.subtrack.domain.entities.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserService {
    Page<UserEntity> getAll(Pageable pageable);

    UserEntity save(UserEntity userEntity);

    void delete(Long id);

    Optional<UserEntity> getUserById(Long id);

    boolean exists(Long id);

    UserEntity partialUpdate(Long id, UserEntity userEntity);

    Optional<UserEntity> getUserByEmail(String email);
}
