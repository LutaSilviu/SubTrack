package com.proiect.subtrack.controllers;

import com.proiect.subtrack.domain.dto.AuthResponse;
import com.proiect.subtrack.domain.dto.UserDto;
import com.proiect.subtrack.domain.entities.UserEntity;
import com.proiect.subtrack.mappers.impl.UserMapperImpl;
import com.proiect.subtrack.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "login")
public class AuthController {

    final private AuthService authService;

    @PostMapping("/{email}")
    @Operation(summary = "Get user by email", description = "Returns a single user")
    public ResponseEntity<UserDto> login(@PathVariable String email){
        log.info("Login request received for email: {}", email);
        UserDto userDto = authService.login(email);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
