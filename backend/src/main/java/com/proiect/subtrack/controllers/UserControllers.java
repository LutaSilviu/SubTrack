package com.proiect.subtrack.controllers;

import com.proiect.subtrack.domain.dto.UserDto;
import com.proiect.subtrack.domain.entities.UserEntity;
import com.proiect.subtrack.mappers.impl.UserMapperImpl;
import com.proiect.subtrack.services.UserService;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController()
@RequestMapping("/users")
@Slf4j
@Tag(name = "Users", description = "User management endpoints")
public class UserControllers {


    private final UserService userService;
    private final UserMapperImpl userMapper;

    public UserControllers(UserService userService, UserMapperImpl userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    @Operation(summary = "Create user", description = "Create a new user from DTO")
    public ResponseEntity<@NonNull UserDto> createUser(@RequestBody UserDto userDto){
        log.info("Creating new user with email: {}", userDto.getEmail());
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity responseEntity = userService.save(userEntity);
        log.debug("User created successfully with ID: {}", responseEntity.getUserId());
        return new ResponseEntity<>(userMapper.mapTo(responseEntity), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get users ", description = "Get all users")
    public List<@NonNull  UserDto> getAllUsers(){
        log.debug("Fetching all users");
        List<@NonNull UserEntity> userEntityList = userService.getAll();
        return userEntityList.stream().map(userMapper::mapTo).toList();

    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id", description = "Returns a single user by id")
    public ResponseEntity<@NonNull  UserDto> getUsersById(@PathVariable("id") Long id){
        log.debug("Fetching user by ID: {}", id);
        Optional<UserEntity> userEntity = userService.getUserById(id);
        return userEntity.map(userEntity1 -> {
            log.debug("User found with ID: {}", id);
            return new ResponseEntity<>(userMapper.mapTo(userEntity1), HttpStatus.OK);
        }).orElseGet(() -> {
            log.warn("User not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Deletes a user by id")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        log.info("Deleting user with ID: {}", id);
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partial update user", description = "Partially updates a user by id")
    public ResponseEntity<@NonNull UserDto> partialUpdateUser(@PathVariable("id")Long id, @RequestBody UserDto userDto){
        log.info("Partial update request for user ID: {}", id);
        if(!userService.exists(id)) {
            log.warn("User not found for partial update, ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity updatedUserEntity = userService.partialUpdate(id, userEntity);
        log.debug("User partially updated successfully, ID: {}", id);
        return new ResponseEntity<>(userMapper.mapTo(updatedUserEntity), HttpStatus.OK);
    }
}
