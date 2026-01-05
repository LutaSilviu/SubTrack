package com.proiect.subtrack.services;

import com.proiect.subtrack.domain.entities.UserEntity;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserEntity> getAll();

    UserEntity save(UserEntity userEntity);

    void delete(Long id);

    Optional<UserEntity> getUserById(Long id);

    boolean exists(Long id);

    UserEntity partialUpdate(Long id, UserEntity userEntity);

}
