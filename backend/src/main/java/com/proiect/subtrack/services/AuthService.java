package com.proiect.subtrack.services;

import com.proiect.subtrack.domain.dto.AuthResponse;
import com.proiect.subtrack.domain.dto.UserDto;
import com.proiect.subtrack.domain.entities.UserEntity;

import java.util.Optional;

public interface AuthService {

    UserDto login(String email);
}
