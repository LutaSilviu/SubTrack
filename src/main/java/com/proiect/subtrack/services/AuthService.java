package com.proiect.subtrack.services;

import com.proiect.subtrack.domain.entities.UserEntity;

import java.util.Optional;

public interface AuthService {

    Optional<UserEntity> login(String email);
}
